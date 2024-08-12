(ns app.petit-plat.authentication.domain.account-test
  (:require [clojure.test :refer :all]
            [malli.core :as m]
            [app.petit-plat.authentication.domain.account :refer :all]))

(deftest validate-login-input-test
  (testing "validate-login-input function"
    (testing "should return true for valid input"
      (is (true? (validate-login-input {:email "valid@example.com"
                                        :password "ValidPass123"}))))

    (testing "should return false for missing email"
      (is (false? (validate-login-input {:password "ValidPass123"}))))

    (testing "should return false for invalid email format"
      (is (false? (validate-login-input {:email "invalid-email"
                                         :password "ValidPass123"}))))

    (testing "should return false for missing password"
      (is (false? (validate-login-input {:email "valid@example.com"}))))

    (testing "should return false for password shorter than 8 characters"
      (is (false? (validate-login-input {:email "valid@example.com"
                                         :password "short"}))))

    (testing "should return false for password longer than 50 characters"
      (is (false? (validate-login-input {:email "valid@example.com"
                                         :password (apply str (repeat 51 "a"))}))))))

(comment
  (run-all-tests))