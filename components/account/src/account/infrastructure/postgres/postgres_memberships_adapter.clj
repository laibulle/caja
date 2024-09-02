(ns account.infrastructure.postgres.postgres-memberships-adapter
  (:require
   [postgres-db.interface :as db]
   [honey.sql :as sql]))

(def table-name :memberships)

(defn insert-membership [data]
  (-> {:insert-into table-name
       :values [data]}
      (sql/format)
      (db/execute!)))

(comment
  (insert-membership {}))