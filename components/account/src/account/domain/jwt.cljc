(ns account.domain.jwt
  (:require [malli.core :as m]))


(def AccountJwtInput
  [:map
   [:account-id :string]])

(defn validate-account-jwt-input [input]
  (m/validate AccountJwtInput input))

(comment
  (validate-account-jwt-input {}))