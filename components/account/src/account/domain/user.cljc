(ns account.domain.user
  (:require [malli.core :as m]
            [malli.generator :as mg]
            [malli.clj-kondo :as mc]
            [clojure.string :as str]))

(def UserId [:int {:min 1}])
(def String100 [:string])
(def Password [:string {:min 8 :max 50}])

(def EmailAddress  [:re {:description "https://github.com/gfredericks/test.chuck/issues/46"
                         :gen/fmap '(constantly "random@example.com")
                         :error/message "should be email"}
                    #"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,63}$"])

(def ValidatedEmailAddress  [:re {:description "https://github.com/gfredericks/test.chuck/issues/46"
                                  :gen/fmap '(constantly "random@example.com")
                                  :error/message "should be email"}
                             #"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,63}$"])

(defn generate-confirmation-token []
  (-> (random-uuid)
      str
      (str/replace  "-" "")))

(def User
  [:map
   [:id UserId]
   [:name String100]
   [:email String100]
   [:confirmed :boolean]
   [:confirmation-token String100]
   [:new-email String100]
   [:email-confirmation-token String100]
   [:password Password]])

(def RegisterUserInput
  [:map
   [:name String100]
   [:email EmailAddress]
   [:password Password]])

(def UpdatePasswordInput
  [:map
   [:password String100]
   [:email EmailAddress]
   [:token :string]])

(defn validate-update-password-input [input]
  (m/validate UpdatePasswordInput input))

(defn validate-register-user-input [input]
  (m/validate RegisterUserInput input))

(def ConfirmUserEmailInput
  [:map
   [:email String100]
   [:token String100]])

(defn validate-confirmation-email-input [input]
  (m/validate ConfirmUserEmailInput input))

(def DeleteUserInput
  [:map
   [:user-id :pos-int]])

(defn validate-delete-user-input [input]
  (m/validate DeleteUserInput input))

(comment
  (-> (mc/collect *ns*) (mc/linter-config))
  (mc/emit!)
  (generate-confirmation-token)

  (mg/generate EmailAddress)


  (mg/generate User)
  (m/validate User {:id -9}))