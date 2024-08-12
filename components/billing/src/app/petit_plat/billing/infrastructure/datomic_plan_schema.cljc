(ns app.petit-plat.billing.infrastructure.datomic-plan-schema
  (:require
   [cuid.core :as c]
   [app.petit-plat.datomic-db.interface :as db]))

(def prefix "plan")

(def plan-schema
  [{:db/ident       :plan/sku
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity
    :db/doc         "Plan SKU"}

   {:db/ident       :plan/name
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity
    :db/doc         "Plan name"}])

(defn domain-to-db [input]
  (db/domain-to-db prefix input))

(defn db-to-domain [input]
  (db/db-to-domain input))

(comment
  (count (c/cuid)))