(ns account.use-cases.register-user-in-db-use-case-test
  (:require
   [clojure.test :refer :all]
   [account.use-cases.register-user-in-db-use-case :as use-case]
   [account.infrastructure.datomic-user-schema :as user-schema]
   [app.petit-plat.email.interface :as em]
   [account.domain.user :as user]))

(def valid-input {:name "John doe" :email "test@gmail.com" :password "ValidPassword#123"})
(def invalid-input {:email "test@gmail.com" :password "short"})

(deftest user-valid?-test
  (testing "Valid user input"
    (with-redefs [user/validate-register-user-input (constantly true)]
      (is (= {:data valid-input} (#'use-case/user-valid? {:data valid-input}))))
    (testing "Invalid user input"
      (with-redefs [user/validate-register-user-input (constantly false)]
        (is (= {:errors [:invalid-user]} (#'use-case/user-valid? {:data invalid-input})))))))

(deftest user-exists?-test
  (testing "User does not exist"
    (with-redefs [user-schema/get-user-by-email (constantly nil)]
      (is (= {:data valid-input} (#'use-case/user-exists? {:data valid-input}))))
    (testing "User already exists"
      (with-redefs [user-schema/get-user-by-email (constantly {:email "test@gmail.com"})]
        (is (= {:errors [:email-already-taken]} (#'use-case/user-exists? {:data valid-input})))))))

(deftest save-in-db-test
  (testing "Save user in database"
    (with-redefs [user-schema/insert-user (constantly true)]
      (is (= {:data valid-input} (#'use-case/save-in-db {:data valid-input}))))))

(deftest send-confirmation-email-test
  (testing "Send a confirmation email"
    (with-redefs [em/send-message (constantly true)]
      (is (= {:data valid-input} (#'use-case/send-confirmation-email {:data valid-input})))))
  (testing "Fail sending a confirmation email"
    (with-redefs [em/send-message (constantly {:errors [{:code 0}]})]
      (is (= {:errors [{:code 0}]} (#'use-case/send-confirmation-email {:data valid-input}))))))

(deftest execute-test
  (testing "Successful user registration"
    (with-redefs [user/validate-register-user-input (constantly true)
                  user-schema/get-user-by-email (constantly nil)
                  em/send-message (constantly true)
                  user-schema/insert-user (constantly true)]
      (is (= valid-input (use-case/execute valid-input)))))
  (testing "User validation fails"
    (with-redefs [user/validate-register-user-input (constantly false)]
      (is (= {:errors [:invalid-user]} (use-case/execute invalid-input)))))
  (testing "User already exists"
    (with-redefs [user/validate-register-user-input (constantly true)
                  user-schema/get-user-by-email (constantly {:email "test@gmail.com"})]
      (is (= {:errors [:email-already-taken]} (use-case/execute valid-input))))))

(comment
  (run-tests))
