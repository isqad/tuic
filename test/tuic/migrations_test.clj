(ns tuic.migrations-test
  (:require [clojure.test :refer :all]
            [tuic.migrations :refer :all]
            [clojure.java.io :refer :all])
  (:import [org.joda.time DateTime DateTimeUtils]
           [org.joda.time.format DateTimeFormat]))

(deftest migration?-test
  (testing "up migration"
    (let [migrations (create! (timestamp))
          up-migration-file (:up migrations)
          down-migration-file (:down migrations)]
      (is (= (migration? re-up up-migration-file) true))
      (is (= (migration? re-down down-migration-file) true))
      (is (= (migration? re-up down-migration-file) false))
      (is (= (migration? re-down up-migration-file) false)))))

(deftest timestamp-test
  (testing "generate timestamp"
    (try
      (DateTimeUtils/setCurrentMillisFixed 1000)
      (let [current-timestamp (. (DateTimeFormat/forPattern "yyyyMMddHHmmss") print (DateTime.))]
        (is (= (timestamp) current-timestamp)))
      (finally (DateTimeUtils/setCurrentMillisSystem)))))

(deftest create-test
  (testing "creating migrations"
    (try
      (DateTimeUtils/setCurrentMillisFixed 1000)
      (let [current-timestamp (. (DateTimeFormat/forPattern "yyyyMMddHHmmss") print (DateTime.))
            migrations (create! current-timestamp)
            up-file (:up migrations)
            down-file (:down migrations)]
        (is (= (.exists up-file)) true)
        (is (= (.exists down-file)) true)
        (delete-file (.getAbsolutePath up-file))
        (delete-file (.getAbsolutePath down-file)))
      (finally (DateTimeUtils/setCurrentMillisSystem)))))
