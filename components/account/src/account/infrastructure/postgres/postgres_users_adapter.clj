(ns account.infrastructure.postgres.postgres-users-adapter
  (:require
   [postgres-db.interface :as db]
   ;[honey.sql :as sql]
   ))


;; (defn get-user-by-email [email]
;;   (-> {:select [:a :b :c]
;;        :from   [:foo]
;;        :where  [:= :foo.a "baz"]}
;;       (sql/format)
;;       (jdbc/execute! conn)))

(comment
  (db/init {:url "jdbc:postgresql://localhost:5437/petitplat_dev" :user "postgres" :password "postgres"})
  (db/fetch-data))