(ns account.use-cases.confirm-user-in-db-use-case
  (:require [account.domain.user :as user]
            [common.interface :refer [handle-errors collect-result]]
            [account.infrastructure.datomic-user-schema :as user-schema]))

(defn- input-valid? [{:keys [data]}]
  (if (true? (user/validate-confirmation-email-input data))
    {:data data}
    {:errors [:invalid-confirmation-token-input]}))

(defn- get-user [{:keys [data]}]
  (let [user (user-schema/get-user-by-email-confirmation-token (:email-confirmation-token data))]
    (if (some? user)
      {:data (assoc-in data [:user] user)}
      {:errors [:email-confirmation-token-not-found]})))

(defn- update-user [{:keys [data]}]
  {:data {:user (-> data
                    (assoc-in  [:user :comfirmed] true)
                    (assoc-in  [:user :confirmation-token] nil)
                    (user-schema/update-user))}})

(defn execute [input]
  (-> {:data input}
      (handle-errors input-valid?)
      (handle-errors get-user)
      (handle-errors update-user)
      collect-result))

(comment [])