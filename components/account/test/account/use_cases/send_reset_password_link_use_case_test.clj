(ns account.use-cases.send-reset-password-link-use-case-test
  (:require
   [clojure.test :refer :all]
   [account.use-cases.send-reset-password-link-use-case :as use-case]
   [account.infrastructure.postgres.postgres-users-adapter :as ua]
   [account.infrastructure.postgres.postgres-password-reset-request-adapter :as ra]
   [messages.interface :as mi]
   [taoensso.tower :as tower :refer (with-tscope)]
   [next.jdbc :as jdbc]
   [postgres-db.interface :as db]
   [clojure.java.jdbc :as jdbc-mock]))


(deftest test-execute
  (testing "When user is not found"
    (with-redefs [ua/get-user-by-email (fn [_ _] nil)]
      (let [result (use-case/execute {:email "nonexistent@example.com"})]
        (is (= {:errors [:user-not-found]} result)))))

  (testing "When there are too many pending reset requests"
    (with-redefs [ua/get-user-by-email (fn [_ _] {:id 1 :email "user@example.com"})
                  ra/list-pending-requests-for-user (fn [_ _] [{:id 1} {:id 2} {:id 3}])]
      (let [result (use-case/execute {:email "user@example.com"})]
        (is (= {:errors [:to-many-requests]} result)))))

  (testing "Successful reset password request"
    (with-redefs [ua/get-user-by-email (fn [_ _] {:id 1 :email "user@example.com"})
                  ra/list-pending-requests-for-user (fn [_ _] [])
                  ra/insert-request (fn [_ _] {:id 1 :user-id 1 :token "token12345"})
                  mi/send-email-from-template (fn [_] :email-sent)]
      (let [result (use-case/execute {:email "user@example.com"})]
        (is (= {:request {:id 1 :user-id 1 :token "token12345"}} result))))))

(comment
  (run-tests))