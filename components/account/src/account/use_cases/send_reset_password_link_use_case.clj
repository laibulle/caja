(ns account.use-cases.send-reset-password-link-use-case
  (:require
   [account.infrastructure.postgres.postgres-users-adapter :as ua]
   [account.infrastructure.postgres.postgres-password-reset-request-adapter :as ra]
   [common.interface :refer [=> collect-result]]
   [messages.interface :as mi]
   [taoensso.tower :as tower :refer (with-tscope)]
   [next.jdbc :as jdbc]
   [postgres-db.interface :as db])
  (:import [java.net URLEncoder]))

(def t (tower/make-t
        {:dictionary
         {:fr   {:reset-password-email {:subject "Changez votre mot de passe"
                                        :intro ""
                                        :outro ""
                                        :reset-password "Changer votre mot de passe"
                                        :instructions "Vous pouvez changer votre mot de passe en cliquant sur le lien ci-dessous lien si dessous :"}
                 :missing  "|Missing translation: [%1$s %2$s %3$s]|"}}
         :dev-mode? true
         :fallback-locale :fr}))

(def user-not-found :user-not-found)

(defn check-threshold [input]
  input)

(defn create-reset-request [input]
  (let [request-data  {:user-id (get-in input [:user :id]) :token (apply str (repeatedly 40 #(rand-nth "abcdefghijklmnopqrstuvwxyz0123456789")))}
        request (ra/insert-request (:tx input) request-data)]
    (assoc input :request request)))

(defn send-email [input]
  (with-tscope :reset-password-email
    (let [link (mi/create-email-link (str "/reset-password?token=" (get-in input [:request :token]) "&email=" (URLEncoder/encode (get-in input [:user :email]) "UTF-8")))
          result (mi/send-email-from-template {:to (get-in input [:user :email])
                                               :subject (t :fr :subject)
                                               :variables {:title (t :fr :subject)
                                                           :intro [(t :fr :intro)]
                                                           :outro [(t :fr :outro)]
                                                           :action [{:instructions (t :fr :instructions)
                                                                     :button [{:link link :text (t :fr :reset-password) :color "blue"}]}]}})]))
  input)

(defn get-user-by-email [input]
  (let [user (ua/get-user-by-email (:tx input) (get-in input [:input :email]))]
    (if (not (nil? user))
      (assoc input :user user)
      {:errors [user-not-found]})))


(defn execute [input]
  (jdbc/with-transaction [tx @db/datasource]
    (-> {:input input :tx tx}
        (get-user-by-email)
        (=> check-threshold)
        (=> create-reset-request)
        (=> send-email)
        collect-result)))

(comment
  (execute {:email "fnreifnre@gmail.com"}))