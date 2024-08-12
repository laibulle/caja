(ns app.petit-plat.authentication.domain.jwt-test
  (:require [clojure.test :refer :all]
            [malli.core :as m]
            [app.petit-plat.authentication.domain.jwt :refer :all]))

(deftest validate-account-jwt-input-test
  (testing "validate-account-jwt-input function"
    (testing "should return true for valid input"
      (is (true? (validate-account-jwt-input {:account-id "12345"}))))

    (testing "should return false for missing account-id"
      (is (false? (validate-account-jwt-input {}))))

    (testing "should return false for non-string account-id"
      (is (false? (validate-account-jwt-input {:account-id 12345}))))

    (testing "should return false for nil account-id"
      (is (false? (validate-account-jwt-input {:account-id nil}))))))


(comment
  (run-all-tests))