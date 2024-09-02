(ns account.infrastructure.postgres.postgres-organizations-adapter
  (:require
   [postgres-db.interface :as db]
   [honey.sql :as sql]
   [clojure.set :as set]))

(defn db-to-domain-organization [db-org]
  (-> db-org
      (set/rename-keys {:organizations/id :id
                        :organizations/slug :slug
                        :organizations/name :name
                        :organizations/created_at :created-at
                        :organizations/updated_at :updated-at})))

(def table-name :organizations)

(defn insert-organization [data]
  (-> {:insert-into table-name
       :values [data]}
      (sql/format)
      (db/execute!)
      (db-to-domain-organization)))

(comment
  (insert-organization {:name "hello"}))