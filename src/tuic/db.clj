(ns tuic.db
  (:require [clojure.java.jdbc :as sql]))

(def pg {:dbtype "postgresql"
         :dbname "tuic"
         :host   "pg"
         :user   "tuic"})

(defn reset-db!
  []
  (let [dbname (:dbname pg)
        conn (assoc pg :dbname "postgres")]
    (sql/execute! conn (str "DROP DATABASE " dbname) {:transaction? false})
    (sql/execute! conn (str "CREATE DATABASE " dbname) {:transaction? false})))

