(ns account.interface
  (:require
   [malli.core :as m]
   [malli.clj-kondo :as mc]
   [account.domain.user :as user]
   [account.domain.account :as account]
   [account.domain.password-reset-request :as password-reset-request]
   #?(:clj [account.use-cases.update-password-use-case :as update-password-use-case])
   #?(:clj [account.use-cases.send-reset-password-link-use-case :as send-reset-password-link-use-case])
   #?(:clj [account.use-cases.authenticate-use-case :as authenticate-use-case])
   #?(:clj [account.use-cases.register-user-in-db-use-case :as register-user-in-db-use-case])
   #?(:clj [account.use-cases.confirm-user-in-db-use-case :as confirm-user-in-db-use-case])))

#?(:clj (do
          (m/=>  register-user-in-db [:=> [:cat user/RegisterUserInput] [:or nil user/User]])
          (defn register-user-in-db [input]
            (register-user-in-db-use-case/execute input))

          (m/=>  confirm-user-in-db [:=> [:cat user/ConfirmUserEmailInput] [:or nil user/User]])
          (defn confirm-user-server [input]
            (confirm-user-in-db-use-case/execute input))

          (m/=>  authenticate-server [:=> [:cat account/LoginInput] [:or nil account/LoginResponse]])
          (defn authenticate-server [input]
            (authenticate-use-case/execute input))

          (m/=>  send-reset-password-link-server [:=> [:cat password-reset-request/PasswordResetRequestInput] [:or nil account/LoginResponse]])
          (defn send-reset-password-link-server [input]
            (send-reset-password-link-use-case/execute (input)))

          (m/=>  reset-password-server [:=> [:cat user/UpdatePasswordInput] [:or nil account/LoginResponse]])
          (defn reset-password-server [input]
            (update-password-use-case/execute input))

          (defn delete-account-server [])))

(comment
  (mc/emit!))
