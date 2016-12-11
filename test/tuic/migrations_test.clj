(ns tuic.migrations-test
  (:require [clojure.test :refer :all]
            [tuic.migrations :refer :all]
            [clojure.java.io :refer :all])
  (:import [org.joda.time DateTime DateTimeUtils]
           [org.joda.time.format DateTimeFormat]))

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
            _ (create current-timestamp)
            migrations-path "resources/migrations/"
            up-filename (str migrations-path current-timestamp ".up.sql")
            down-filename (str migrations-path current-timestamp ".down.sql")
            migration-file-up (as-file up-filename)
            migration-file-down (as-file down-filename)]
        (is (= (.exists migration-file-up) true))
        (is (= (.exists migration-file-down) true))
        (delete-file up-filename)
        (delete-file down-filename))
      (finally (DateTimeUtils/setCurrentMillisSystem)))))
