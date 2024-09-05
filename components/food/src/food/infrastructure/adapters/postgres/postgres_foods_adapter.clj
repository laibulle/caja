(ns food.infrastructure.adapters.postgres.postgres-foods-adapter
  (:require
   [postgres-db.interface :as db]
   [honey.sql :as sql]
   [clojure.set :as set]
   [next.jdbc :as jdbc]))

(def table-name :foods)

(def attributes [:id :name :member_id :created_at :updated_at])

(defn domain-food-to-db [user]
  (-> user
      (set/rename-keys {:member-id :member_id
                        :created-at :created_at
                        :updated-at :updated_at})))

(defn db-to-food-domain [db-user]
  (-> db-user
      (set/rename-keys {:recipes/id :id
                        :recipes/name :name
                        :recipes/member_id :member-id
                        :recipes/created_at :created-at
                        :recipes/updated_at :updated-at})))

(defn insert-food [tx data]
  (let [query {:insert-into table-name
               :values [(domain-food-to-db data)]}
        res (db/execute-one! tx (sql/format query) {:return-keys true})]
    (db-to-food-domain res)))

(comment
  (comment
    (jdbc/with-transaction [tx @db/datasource]
      ;(insert-user tx {:name "hello" :email "j@gmdsail.com" :confirmed-at (Timestamp. (System/currentTimeMillis))})
      (insert-food tx {:member-id 1 :name "sample recipe"}))))