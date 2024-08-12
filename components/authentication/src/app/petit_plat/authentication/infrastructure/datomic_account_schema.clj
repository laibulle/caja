(ns app.petit-plat.authentication.infrastructure.datomic-account-schema
  (:require
   [app.petit-plat.datomic-db.interface :as db]))

(def account-schema
  [{:db/ident       :user/id
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity
    :db/doc         "User ID"}

   {:db/ident       :user/password
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "User password"}

   {:db/ident       :user/email
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity
    :db/doc         "User email address"}


   {:db/ident       :user/confirmed
    :db/valueType   :db.type/boolean
    :db/cardinality :db.cardinality/one
    :db/doc         "User is confirmed"}])

(defn domain-to-db [input]
  (db/domain-to-db "user" input))

(defn db-to-domain [input]
  (db/db-to-domain input))

(defn get-account-by-email [email]
  (let [[result] (db/list-in-db '[:find (pull ?e [*])
                                  :in $ ?email
                                  :where
                                  [?e :user/email ?email]] email)]
    (if (= (count result) 0)
      nil
      (db-to-domain (first result)))))

(comment
  (get-account-by-email "blabla"))