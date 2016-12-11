(ns tuic.core
  (:require [clojure.java.jdbc :as sql]
            [compojure.core :refer :all]
            [compojure.route :as route]))

(defn app-handler
  []
  (str "<h1>Fruits</h1><br/ >"))

(defroutes app
  (GET "/" [] (app-handler))
  (route/not-found "<h1>404 Page not found</h1>"))
