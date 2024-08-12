(ns app.petit-plat.account.interface
  (:require
   [malli.core :as m]
   [malli.clj-kondo :as mc]
   [app.petit-plat.account.domain.user :as user]
   [app.petit-plat.account.use-cases.register-user-in-db-use-case :as register-user-in-db-use-case]
   [app.petit-plat.account.use-cases.confirm-user-in-db-use-case :as confirm-user-in-db-use-case]))

(defn hello [] "Hello world")

(m/=>  register-user-in-db [:=> [:cat user/RegisterUserInput] [:or nil user/User]])
(defn register-user-in-db [input]
  (register-user-in-db-use-case/execute input))

(m/=>  confirm-user-in-db [:=> [:cat user/ConfirmUserEmailInput] [:or nil user/User]])
(defn confirm-user-server [input]
  (confirm-user-in-db-use-case/execute input))

(defn reset-password-server [])

(defn forgotten-password-server [])

(defn delete-account-server [])

(defn login-server [])

(comment
  (mc/emit!))
