(ns account.infrastructure.datomic-organization-schema
  (:require
   [cuid.core :as c]
   [app.petit-plat.datomic-db.interface :as db]))

(def prefix "organization")

(def orgnization-schema
  [{:db/ident       :organization/id
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity
    :db/doc         "Organization ID"}

   {:db/ident       :organization/owner-id
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity
    :db/doc         "Organization owner ID"}

   {:db/ident       :organization/name
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "Name for organization"}])


(defn domain-to-db [input]
  (db/domain-to-db prefix input))

(defn db-to-domain [input]
  (db/db-to-domain input))

(defn get-organization-by-id [id]
  (let [[result] (db/list-in-db '[:find (pull ?e [*])
                                  :in $ ?id
                                  :where
                                  [?e :organization/id ?id]] id)]
    (if (= (count result) 0)
      nil
      (db-to-domain (first result)))))

(defn insert-organization [input]
  (-> input (assoc :id (c/cuid))
      domain-to-db
      db/insert-one
      db-to-domain))


(comment
  (insert-organization {:name "hello"})
  (get-organization-by-id "clzj11zjn0002783868y7a5qx"))