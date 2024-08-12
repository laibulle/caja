(ns billing.infrastructure.datomic-organization-plan-schema
  (:require
   [cuid.core :as c]
   [app.petit-plat.datomic-db.interface :as db]))

(def prefix "organization-plan")

(def organization-plan-schema
  [{:db/ident       :organization-plan/plan-sku
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity
    :db/doc         "Plan SKU"}

   {:db/ident       :organization-plan/organization-id
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity
    :db/doc         "Organization ID"}])

(defn domain-to-db [input]
  (db/domain-to-db prefix input))

(defn db-to-domain [input]
  (db/db-to-domain input))

(comment)