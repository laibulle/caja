(ns account.domain.account
  (:require [malli.core :as m]
            [malli.generator :as mg]
            [malli.clj-kondo :as mc]
            [clojure.string :as str]))


(def Password [:string {:min 8 :max 50}])

(def EmailAddress  [:re {:description "https://github.com/gfredericks/test.chuck/issues/46"
                         :gen/fmap '(constantly "random@example.com")
                         :error/message "should be email"}
                    #"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,63}$"])

(def LoginInput
  [:map
   [:email EmailAddress]
   [:password Password]])

(def LoginResponse
  [:map
   [:jwt :string]])

(defn validate-login-input [input]
  (m/validate LoginInput input))


(comment
  (validate-login-input {:email "frefre@frfrefr.fr" :password "nconfiorn"}))