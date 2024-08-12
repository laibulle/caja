(ns app.petit-plat.authentication.use-cases.authenticate-use-case-test
  (:require [clojure.test :refer :all]
            [app.petit-plat.authentication.use-cases.authenticate-use-case :refer :all]
            [app.petit-plat.authentication.infrastructure.jwt :refer [generate-account-token]]
            [app.petit-plat.authentication.domain.account :refer [validate-login-input]]
            [app.petit-plat.authentication.infrastructure.datomic-account-schema :refer [get-account-by-email]]
            [app.petit-plat.password-hash.interface :as ph]
            [app.petit-plat.common.interface :refer [handle-errors collect-result]]))

(deftest execute-test
  (testing "execute function"
    (testing "should return JWT for valid credentials"
      ;; Mock the necessary functions
      (with-redefs [validate-login-input (fn [_] true)
                    get-account-by-email (fn [_] {:id 1 :password "hashedpassword"})
                    ph/check (fn [_ _] true)
                    generate-account-token (fn [_] "mocked-jwt-token")]
        (let [result (execute {:email "valid@example.com" :password "ValidPass123"})]
          (is (= {:jwt "mocked-jwt-token"} result)))))

    (testing "should return :invalid-credentials for non-existent account"
      (with-redefs [validate-login-input (fn [_] true)
                    get-account-by-email (fn [_] nil)]
        (let [result (execute {:email "nonexistent@example.com" :password "password"})]
          (is (= {:errors [:invalid-credentials]} result)))))

    (testing "should return :invalid-credentials for incorrect password"
      (with-redefs [validate-login-input (fn [_] true)
                    get-account-by-email (fn [_] {:id 1 :password "hashedpassword"})
                    ph/check (fn [_ _] false)]
        (let [result (execute {:email "valid@example.com" :password "wrongpassword"})]
          (is (= {:errors [:invalid-credentials]} result)))))

    (testing "should return :invalid-input for invalid login input"
      (with-redefs [validate-login-input (fn [_] false)]
        (let [result (execute {:email "invalid-email" :password "short"})]
          (is (= {:errors [:invalid-input]} result)))))))

(comment
  (run-all-tests))