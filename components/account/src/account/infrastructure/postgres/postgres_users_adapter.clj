(ns account.infrastructure.postgres.postgres-users-adapter
  (:require
   [postgres-db.interface :as db]
   [honey.sql :as sql]))

(def table-name :users)
;; (defn get-user-by-email [email]
;;   (-> {:select [:a :b :c]
;;        :from   [:foo]
;;        :where  [:= :foo.a "baz"]}
;;       (sql/format)
;;       (jdbc/execute! conn)))

(defn insert-user [data]
  (-> {:insert-into table-name
       :columns [:name :email]
       :values [data]}
      (sql/format)
      (db/execute!)))

(defn get-user-by-email [email]
  (-> {:select [:id :name :email :confirmed_at :confirmation-token :created_at :updated_at]
       :from [table-name]
       :where
       [:= :email email]}
      (sql/format)
      (db/execute-one!)))

(comment
  (db/init {:url "jdbc:postgresql://localhost:5437/petitplat_dev" :user "postgres" :password "postgres"})
  (db/fetch-data)
  (insert-user {:name "hello" :email "test@gmail.com"})
  (get-user-by-email "test@gmail.com"))