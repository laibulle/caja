(ns app.petit-plat.account.domain.user-test
  (:require [clojure.test :as test :refer :all]
            [app.petit-plat.account.domain.user :as user]))

(deftest test-validate-register-user-input
  (testing "Valid input"
    (is (true? (user/validate-register-user-input
                {:name "John Doe"
                 :email "john.doe@example.com"
                 :password "password123"}))))

  (testing "Invalid input - missing fields"
    (is (false? (user/validate-register-user-input
                 {:name "John Doe"
                  :email "john.doe@example.com"}))))

  (testing "Invalid input - invalid email"
    (is (false? (user/validate-register-user-input
                 {:name "John Doe"
                  :email "john.doe@com"
                  :password "password123"}))))

  (testing "Invalid input - password too short"
    (is (false? (user/validate-register-user-input
                 {:name "John Doe"
                  :email "john.doe@example.com"
                  :password "pwd"})))))

(comment
  (run-tests))
