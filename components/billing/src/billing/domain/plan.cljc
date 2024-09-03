(ns billing.domain.plan
  (:require [malli.core :as m]
            [malli.generator :as mg]
            [malli.clj-kondo :as mc]
            [clojure.instant :as inst]))

(def SKU [:string {:min 5 :max 50}])
(def Id [:int {:min 1}])

(def Plan
  [:map
   [:sku SKU]
   [:name :string]])

(def OrganizationPlan
  [:map
   [:organization-id Id]
   [:plan-id SKU]])

(comment
  (mg/generate Plan)
  (mg/generate OrganizationPlan))