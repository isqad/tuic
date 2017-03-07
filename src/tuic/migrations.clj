(ns tuic.migrations
  (:import [org.joda.time DateTime DateTimeUtils]
           [org.joda.time.format DateTimeFormat])
  (:require [clojure.java.jdbc :as sql]
            [clojure.java.io   :as io]
            [tuic.db           :as db]))

(def path "resources/migrations")
(def schema-migrations "public.schema_migrations")
(def re-up #"(\d+)\.up\.sql$")
(def re-down #"(\d+)\.down\.sql$")

(defn timestamp
  []
  (. (DateTimeFormat/forPattern "yyyyMMddHHmmss") print (DateTime.)))

(defn create
  "Creates new migration files"
  [timestamp]
  (with-open [w (io/writer (str path "/" timestamp ".up.sql"))]
    (.write w (str "--put some sql statements for up migrations")))
  (with-open [w (io/writer (str path "/" timestamp ".down.sql"))]
    (.write w (str "--put some sql statements for down migrations"))))

(defn migration-label
  [filename]
  (last (re-find re-up filename)))

(defn create-schema-migrations!
  []
  (sql/db-do-commands db/pg
                      (sql/create-table-ddl
                        "if not exists schema_migrations"
                        [[:label "varchar(255)" "not null" "primary key"]])))

(defn execute-migration!
  "Executes sql statements from file"
  [file]
  (let [label (migration-label (.getName file))]
    (sql/with-db-transaction [trans-conn db/pg]
      (sql/insert! db/pg schema-migrations {:label label})
      (sql/execute! db/pg [(slurp (.getAbsolutePath file))]))))

; TODO: execute-down!
; TODO down!

(defn migrate!
  []
  (create-schema-migrations!)
  (map #(execute-migration! %) (filter (comp migration-label #(.getName %)) (file-seq (io/file path)))))
