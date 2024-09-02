(ns account.infrastructure.postgres.postgres-organizations-adapter
  (:require
   [postgres-db.interface :as db]
   [honey.sql :as sql]))

(def table-name :organizations)

(defn insert-organization [data]
  (-> {:insert-into table-name
       :values [data]}
      (sql/format)
      (db/execute!)))

(comment
  (insert-organization {:name "hello"}))