(ns tuic.core
  (:require [monger.core :as mg]
            [monger.collection :as mc]))

(defn fruits
  []
  (let [conn (mg/connect {:host "mongo"})
        db (mg/get-db conn "monger-test")
        coll "fruits"]
    (mc/insert db coll {:name "Orange" :weight 76.5})
    (mc/find-maps db coll {:name "Orange"})))

(defn app-handler
  [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (str "<h1>Fruits</h1><br/ >" (apply str (fruits)))})
