(ns account.infrastructure.postgres.postgres-organizations-adapter
  (:require
   [postgres-db.interface :as db]
   [honey.sql :as sql]
   [clojure.set :as set]
   [next.jdbc :as jdbc]))

(defn db-to-domain-organization [db-org]
  (println)
  (-> db-org
      (set/rename-keys {:organizations/id :id
                        :organizations/slug :slug
                        :organizations/name :name
                        :organizations/created_at :created-at
                        :organizations/updated_at :updated-at})))

(defn domain-organization-to-db [db-user]
  (-> db-user
      (set/rename-keys {:updated-at :updated_at})))

(def table-name :organizations)

(defn insert-organization [tx data]
  (->> {:insert-into table-name
        :values [data]}
       (sql/format)
       (db/execute! tx)
       (domain-organization-to-db)))

(comment
  (jdbc/with-transaction [tx @db/datasource]
    (insert-organization tx {:slug "sample" :name "hello"})))