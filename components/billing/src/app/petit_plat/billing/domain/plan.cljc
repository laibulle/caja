(ns app.petit-plat.billing.domain.plan
  (:require [malli.core :as m]
            [malli.generator :as mg]
            [malli.clj-kondo :as mc]
            [clojure.instant :as inst]))

(def CUID [:string {:min 25 :max 25}])
(def SKU [:string {:min 5 :max 50}])

(def Plan
  [:map
   [:sku SKU]
   [:name :string]])

(def OrganizationPlan
  [:map
   [:organization-id CUID]
   [:plan-id SKU]])

(comment
  (mg/generate Plan)
  (mg/generate OrganizationPlan))