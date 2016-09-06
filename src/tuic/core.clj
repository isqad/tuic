(ns tuic.core
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [compojure.core :refer :all]
            [compojure.route :as route]))

(defn fruits
  []
  (let [conn (mg/connect {:host "mongo"})
        db (mg/get-db conn "monger-test")
        coll "fruits"]
    (mc/insert db coll {:name "Orange" :weight 76.5})
    (mc/find-maps db coll {:name "Orange"})))

(defn app-handler
  []
  (str "<h1>Fruits</h1><br/ >" (apply str (fruits))))

(defroutes app
  (GET "/" [] (app-handler))
  (route/not-found "<h1>404 Page not found</h1>"))
