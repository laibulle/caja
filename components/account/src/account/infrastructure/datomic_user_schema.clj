(ns account.infrastructure.datomic-user-schema
  (:require
   [cuid.core :as c]
   [app.petit-plat.datomic-db.interface :as db]))

(def user-schema
  [{:db/ident       :user/id
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity
    :db/doc         "User ID"}

   {:db/ident       :user/name
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "Name for the user"}

   {:db/ident       :user/password
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "User password"}

   {:db/ident       :user/email
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity
    :db/doc         "User email address"}

   {:db/ident       :user/new-email
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "New user email address"}

   {:db/ident       :user/confirmed
    :db/valueType   :db.type/boolean
    :db/cardinality :db.cardinality/one
    :db/doc         "User is confirmed"}

   {:db/ident       :user/confirmation-token
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity
    :db/doc         "User confirmation token"}

   {:db/ident       :user/email-confirmation-token
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity
    :db/doc         "New user email address confirmation token"}

   {:db/ident       :user/email-confirmed
    :db/valueType   :db.type/boolean
    :db/cardinality :db.cardinality/one
    :db/doc         "User email is confirmed"}])

(defn domain-to-db [input]
  (db/domain-to-db "user" input))

(defn db-to-domain [input]
  (db/db-to-domain input))

(defn db-users-to-users [input]
  (map (fn [[user]]
         (db-to-domain user))
       input))

(defn get-user-by-email [email]
  (let [[result] (db/list-in-db '[:find (pull ?e [*])
                                  :in $ ?email
                                  :where
                                  [?e :user/email ?email]] email)]
    (if (= (count result) 0)
      nil
      (db-to-domain (first result)))))

(defn get-user-by-email-confirmation-token [email-confirmation-token]
  (let [[result] (db/list-in-db '[:find (pull ?e [*])
                                  :in $ ?email-confirmation-token
                                  :where
                                  [?e :user/email-confirmation-token ?email-confirmation-token]] email-confirmation-token)]
    (if (= (count result) 0)
      nil
      (db-to-domain (first result)))))

(defn insert-user [input]
  (let [email (:email input)
        user (get-user-by-email email)]
    (if (nil? user)
      (-> input (assoc :id (c/cuid))
          domain-to-db
          db/insert-one
          db-to-domain)
      (throw (new Exception (str "The user with email " email " already exists in db"))))))

(defn find-one-by-email [email]
  (-> (db/find-one '[:find (pull ?e [*])
                     :in $ ?email
                     :where
                     [?e :user/email ?email]]
                   email)
      db-to-domain))

(defn list-users []
  (-> (db/list-in-db '[:find (pull ?e [*])
                       :where
                       [?e :user/email]])
      db-users-to-users))

(defn update-user [input]
  (-> input
      domain-to-db
      db/update
      db-to-domain))

(comment
  (insert-user {:email "gui@gmail.com" :password "nfioenfiore" :name "John"})
  (get-user-by-email "gui@gmail.com")
  (let [user (get-user-by-email "gui@gmail.com")]
    (update-user (assoc user :name "updated")))
  (get-user-by-email "do not exists")


  (list-users)
  (find-one-by-email "gui@gmail.com"))