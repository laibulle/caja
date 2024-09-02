(ns account.infrastructure.postgres.postgres-memberships-adapter
  (:require
   [postgres-db.interface :as db]
   [honey.sql :as sql]
   [clojure.set :as set]
   [next.jdbc :as jdbc]))

(def table-name :memberships)

(defn domain-membership-to-db [membership]
  (-> membership
      (set/rename-keys {:user-id :user_id
                        :organization-id :organization_id
                        :updated-at :updated_at})))

(defn db-to-domain-membership [membership]
  (-> membership
      (set/rename-keys {})))

(defn insert-membership [tx data]
  (let [query {:insert-into table-name
               :values [(domain-membership-to-db data)]}
        res (db/execute-one! tx (sql/format query) {:return-keys true})]
    (db-to-domain-membership res)))

(comment
  (jdbc/with-transaction [tx @db/datasource]
    (insert-membership tx {})))