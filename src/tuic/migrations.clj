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

(defn create!
  "Creates new migration files"
  [timestamp]
  (let [up-migration (str path "/" timestamp ".up.sql")
        down-migration (str path "/" timestamp ".down.sql")]
    (with-open [w (io/writer up-migration)]
      (.write w (str "--put some sql statements for up migrations")))
    (with-open [w (io/writer down-migration)]
      (.write w (str "--put some sql statements for down migrations")))
    {:up (io/file up-migration) :down (io/file down-migration)}))

(defn migration-label
  [filename re-filter]
  (last (re-find re-filter filename)))

(defn migration?
  [direction file]
  (-> file
      .getName
      (migration-label direction)
      some?))

(defn create-schema-migrations!
  []
  (sql/db-do-commands db/pg
                      (sql/create-table-ddl
                        "if not exists schema_migrations"
                        [[:label "varchar(255)" "not null" "primary key"]])))
; TODO: multimethod
(defn execute-migration!
  "Executes sql statements from file"
  [direction file]
  (let [label (migration-label (.getName file) direction)]
    (sql/with-db-transaction [trans-conn db/pg]
      (if (= direction re-up)
        (sql/insert! db/pg schema-migrations {:label label})
        (sql/delete! db/pg schema-migrations ["label = ?" label]))
      (sql/execute! db/pg [(slurp (.getAbsolutePath file))]))))

(defn execute-migrations!
  [direction]
  (->> (file-seq (io/file path))
       (filter (partial migration? direction))
       (map #(execute-migration! direction %))))

(defn migrate!
  []
  (create-schema-migrations!)
  (execute-migrations! re-up))

(defn down!
  []
  (execute-migrations! re-down))
