(ns account.use-cases.authenticate-use-case-test
  (:require
   [clojure.test :refer :all]
   [account.use-cases.authenticate-use-case :as auth]
   [common.interface :refer [collect-result]]
   [account.infrastructure.jwt :as jwt]
   [account.domain.account :as domain]
   [postgres-db.interface :as db]
   [account.infrastructure.postgres.postgres-users-adapter :as ua]
   [next.jdbc :as jdbc]
   [password-hash.interface :as ph]))

;; Mock dependencies
(def mock-datasource (atom nil))

(defn mock-get-user-by-email [tx email]
  (cond
    (= email "valid@example.com") {:id 1 :email "valid@example.com" :password-hash "hashed-password" :confirmed-at "2023-01-01"}
    (= email "unconfirmed@example.com") {:id 2 :email "unconfirmed@example.com" :password-hash "hashed-password" :confirmed-at nil}
    :else nil))

(defn mock-check-password [password hash]
  (and (= password "correct-password") (= hash "hashed-password")))

(defn mock-generate-account-token [account-data]
  "mock-jwt-token")

(defn mock-validate-login-input [input]
  (and (:email input) (:password input)))

(defn mock-with-transaction [transaction-fn datasource]
  (transaction-fn {}))

;; Binding mocks to functions
(with-redefs [db/datasource mock-datasource
              ua/get-user-by-email mock-get-user-by-email
              ph/check mock-check-password
              jwt/generate-account-token mock-generate-account-token
              domain/validate-login-input mock-validate-login-input
              jdbc/with-transaction mock-with-transaction]

  (deftest test-authenticate-use-case
    ;; Test valid credentials
    (testing "Valid credentials"
      (let [result (auth/execute {:email "valid@example.com" :password "correct-password"})]
        (is (nil? (:errors result)))
        (is (= (:data result) {:jwt "mock-jwt-token"}))))

    ;; Test invalid credentials
    (testing "Invalid credentials"
      (let [result (auth/execute {:email "invalid@example.com" :password "incorrect-password"})]
        (is (= (:errors result) [auth/invalid-credentials-error]))))

    ;; Test unconfirmed email
    (testing "Unconfirmed email"
      (let [result (auth/execute {:email "unconfirmed@example.com" :password "correct-password"})]
        (is (= (:errors result) [auth/email-not-confirmed]))))

    ;; Test invalid input
    (testing "Invalid input"
      (with-redefs [domain/validate-login-input (fn [_] false)]
        (let [result (auth/execute {:email nil :password nil})]
          (is (= (:errors result) [:invalid-input])))))))
(comment
  (run-tests))