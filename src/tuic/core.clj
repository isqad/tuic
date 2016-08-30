(ns tuic.core
  (:require [monger.core :as mg]
            [monger.collection :as mc]))

(defn hello-world
  []
  (println "Hello world"))

(defn test-mongo
  []
  (let [conn (mg/connect {:host "mongo"})
        db (mg/get-db conn "monger-test")
        coll "fruits"]
    (mc/insert db coll {:name "Orange" :weight 76.5})
    (mc/find-maps db coll {:name "Orange"})))
