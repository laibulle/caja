(ns email.interface-test
  (:require [clojure.test :as test :refer :all]
            [postal.core :as p]
            [app.petit-plat.email.interface :as email]))

(deftest dummy-test
  (is (= 1 1)))

(deftest test-add-default-from
  (testing "Add default from when not present"
    (is (= {:from "me@draines.com"} (#'email/add-default-from {}))))
  (testing "Do not add default from when  present"
    (is (= {:from "anyemail@gmail.com"} (#'email/add-default-from {:from "anyemail@gmail.com"})))))

(deftest test-send-message
  (testing "Send message is successfull"
    (with-redefs [p/send-message (constantly {:error :SUCCESS})]
      (is (true? (email/send-message {})))))

  (testing "Send message failed"
    (with-redefs [p/send-message (constantly {:error :ANY_ERROR})]
      (is (= {:errors [{:error :ANY_ERROR}]} (email/send-message {}))))))

(comment
  (run-tests))