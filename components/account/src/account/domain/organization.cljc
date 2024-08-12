(ns account.domain.organization
  (:require [malli.core :as m]
            [malli.generator :as mg]
            [malli.clj-kondo :as mc]))

(def UserId [:int {:min 1}])
(def String100 [:string])

(def Organization
  [:map
   [:id UserId]
   [:name String100]])

(comment
  (-> (mc/collect *ns*) (mc/linter-config))
  (mc/emit!)

  (mg/generate Organization)


  (mg/generate Organization)
  (m/validate Organization {:id -9}))