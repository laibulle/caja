(ns account.infrastructure.postgres.postgres-organizations-adapter
  (:require
   [postgres-db.interface :as db]
   [honey.sql :as sql]
   [clojure.set :as set]
   [next.jdbc :as jdbc]))

(def table-name :organizations)

(defn db-to-domain-organization [db-org]
  (-> db-org
      (set/rename-keys {:organizations/id :id
                        :organizations/slug :slug
                        :organizations/name :name
                        :organizations/owner_id :owner-id
                        :organizations/created_at :created-at
                        :organizations/updated_at :updated-at})))

(defn domain-organization-to-db [org]
  (-> org
      (set/rename-keys {:owner-id :owner_id
                        :updated-at :updated_at})))

(defn insert-organization [tx data]
  (let [query {:insert-into table-name
               :values [(domain-organization-to-db data)]}
        res (db/execute-one! tx (sql/format query) {:return-keys true})]
    (db-to-domain-organization res)))

(comment
  (jdbc/with-transaction [tx @db/datasource]
    (insert-organization tx {:owner-id 2 :slug "sample" :name "hello"})))