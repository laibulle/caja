(ns password-hash.interface-test
  (:require [clojure.test :refer :all]
            [password-hash.interface :refer :all]))

(deftest test-encrypt
  (testing "Encryption of a password"
    (let [password "my-secret-password"
          encrypted-password (encrypt password)]
      (is (not= password encrypted-password) "Encrypted password should not be the same as the plain password")
      (is (string? encrypted-password) "Encrypted password should be a string"))))

(deftest test-check
  (testing "Correct password check"
    (let [password "my-secret-password"
          encrypted-password (encrypt password)]
      (is (true? (check password encrypted-password)) "Check should return true for correct password")))

  (testing "Incorrect password check"
    (let [password "my-secret-password"
          encrypted-password (encrypt password)]
      (is (false? (check "wrong-password" encrypted-password)) "Check should return false for incorrect password"))))

(deftest test-idempotency
  (testing "Encrypting the same password should not produce the same result"
    (let [password "my-secret-password"
          encrypted-password-1 (encrypt password)
          encrypted-password-2 (encrypt password)]
      (is (not= encrypted-password-1 encrypted-password-2) "Encrypting the same password twice should produce different results"))))

(run-tests)
