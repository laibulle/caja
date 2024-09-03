(ns account.use-cases.update-password-use-case
  (:require
   [common.interface :refer [=> collect-result]]
   [account.domain.user :as user]
   [password-hash.interface :as ph]
   [account.infrastructure.postgres.postgres-password-reset-request-adapter :as ra]
   [account.infrastructure.postgres.postgres-users-adapter :as ua]
   [next.jdbc :as jdbc]
   [postgres-db.interface :as db]))

(defn- input-valid? [input]
  (if (true? (user/validate-update-password-input (:input input)))
    input
    {:errors [:invalid-input]}))

(defn- hash-password [input]
  (-> input
      (assoc-in [:user :password_hash] (ph/encrypt (get-in input [:input :password])))))

(defn- get-password-request [input]
  (let [request (ra/get-request-by-token (:tx input) (get-in input [:input :token]))]
    (if (nil? request)
      {:errors [:email-already-taken]}
      (assoc input :request request))))

(defn- check-email-and-token [input]
  (if (= (get-in input [:user :email]) (get-in input [:input :email]))
    input
    {:errors [:invalid-request]}))

(defn- get-user [input]
  (let [user (ua/get-user-by-id (:tx input) (get-in input [:user :id]))]
    (if (nil? user)
      {:errors [:user-not-found]}
      (assoc input :user user))))

(defn execute [input]
  (jdbc/with-transaction [tx @db/datasource]
    (-> {:input input :tx tx}
        (=> input-valid?)
        (=> get-password-request)
        (=> get-user)
        (=> check-email-and-token)
        (=> hash-password)
        collect-result)))

(comment
  (execute {:email "jsssa@gmail.com" :token "gbiled331lmpljiahz9t5094240t7ypnpallflmp" :password "new-pwd"}))