(ns account.use-cases.authenticate-use-case-test
  (:require
   [clojure.test :refer :all]
   [account.use-cases.authenticate-use-case :refer :all]
   [account.infrastructure.jwt :as jwt]
   [account.domain.account :as account]
   [postgres-db.interface :as db]
   [account.infrastructure.postgres.postgres-users-adapter :as ua]
   [password-hash.interface :as ph]
   [next.jdbc :as jdbc]))

(defn mock-get-user-by-email [tx email]
  (cond
    (= email "valid@example.com") {:id 1 :email "valid@example.com" :password-hash "hashed-password" :confirmed-at (java.util.Date.)}
    (= email "unconfirmed@example.com") {:id 2 :email "unconfirmed@example.com" :password-hash "hashed-password" :confirmed-at nil}
    :else nil))

(defn mock-check-password [password hash]
  (= password "valid-password"))

(defn mock-generate-account-token [data]
  "mock-jwt-token")

(deftest authenticate-use-case-tests
  (with-redefs [ua/get-user-by-email mock-get-user-by-email
                ph/check mock-check-password
                jwt/generate-account-token mock-generate-account-token
                jdbc/with-transaction (fn [tx-fn] (tx-fn {}))]
    (testing "successful authentication"
      (let [result (execute {:email "valid@example.com" :password "valid-password"})]
        (is (nil? (:errors result)))
        (is (= "mock-jwt-token" (:jwt result)))))

    (testing "authentication fails due to invalid email"
      (let [result (execute {:email "invalid@example.com" :password "valid-password"})]
        (is (= [invalid-credentials-error] (:errors result)))))

    (testing "authentication fails due to invalid password"
      (with-redefs [ph/check (fn [_ _] false)]
        (let [result (execute {:email "valid@example.com" :password "invalid-password"})]
          (is (= [invalid-credentials-error] (:errors result))))))

    (testing "authentication fails due to unconfirmed email"
      (let [result (execute {:email "unconfirmed@example.com" :password "valid-password"})]
        (is (= [email-not-confirmed] (:errors result)))))))


(comment
  (run-tests))