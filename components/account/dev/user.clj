(ns user
  (:require
   [postgres-db.interface :as pi]
   [account.use-cases.register-user-in-db-use-case :as ru]))

(defn init []
  (pi/init {:url "jdbc:postgresql://localhost:5437/petitplat_dev" :user "postgres" :password "postgres"}))

(comment
  (init)
  (ru/execute {:name "John Doe" :email "j@gmail.com" :password "Noirfnefwerf#mopgmtrogmroptgm"}))