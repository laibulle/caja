(ns account.use-cases.update-password-use-case
  (:require
   [common.interface :refer [=> collect-result]]
   [account.domain.user :as user]
   [password-hash.interface :as ph]
   [next.jdbc :as jdbc]
   [postgres-db.interface :as db]))

(defn- input-valid? [input]
  (if (true? (user/validate-update-password-input (:data input)))
    input
    {:errors [:invalid-input]}))

(defn- hash-password [input]
  (-> input
      (assoc-in [:user :password_hash] (ph/encrypt (get-in input [:input :password])))))

(defn execute [input]
  (jdbc/with-transaction [tx @db/datasource]
    (-> {:input input :tx tx}
        (=> input-valid?)
        (=> hash-password)
        collect-result)))

(comment
  (execute {:email "fnreifnre@gmail.com" :token "" :password ""}))