(ns tuic.migrations
  (:import [org.joda.time DateTime DateTimeUtils]
           [org.joda.time.format DateTimeFormat]))

(defn timestamp
  []
  (. (DateTimeFormat/forPattern "yyyyMMddHHmmss") print (DateTime.)))

(defn create
  [timestamp]
  (with-open [w (clojure.java.io/writer (str "resources/migrations/" timestamp ".up.sql"))]
    (.write w (str "--put some sql statements for up migrations")))
  (with-open [w (clojure.java.io/writer (str "resources/migrations/" timestamp ".down.sql"))]
    (.write w (str "--put some sql statements for down migrations"))))
