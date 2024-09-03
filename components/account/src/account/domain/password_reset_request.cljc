(ns account.domain.password-reset-request
  (:require [malli.core :as m]))

(def EmailAddress  [:re {:description "https://github.com/gfredericks/test.chuck/issues/46"
                         :gen/fmap '(constantly "random@example.com")
                         :error/message "should be email"}
                    #"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,63}$"])

(def PasswordResetRequestInput
  [:map
   [:email EmailAddress]])

(defn validate-password-request-input [input]
  (m/validate PasswordResetRequestInput input))

(comment
  (validate-password-request-input {:email "frefre@frfrefr.fr"}))