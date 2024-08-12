(ns account.use-cases.confirm-user-in-db-use-case-test
  (:require
   [clojure.test :refer :all]
   [account.use-cases.confirm-user-in-db-use-case :as use-case]
   [account.domain.user :as user]))

(def valid-input {:email-confirmation-token "test@gmail.com"})
(def invalid-input {})

(deftest input-valid?-test
  (testing "Valid user input"
    (with-redefs [user/validate-confirmation-email-input (constantly true)]
      (is (= {:data valid-input} (#'use-case/input-valid? {:data valid-input})))))
  (testing "Invalid user input"
    (with-redefs [user/validate-confirmation-email-input (constantly false)]
      (is (= {:errors [:invalid-confirmation-token-input]} (#'use-case/input-valid? {:data invalid-input}))))))

(comment
  (run-tests))