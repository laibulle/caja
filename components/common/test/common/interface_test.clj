(ns common.interface-test
  (:require [clojure.test :refer :all]
            [common.interface :refer :all]
            [malli.core :as m]))

(deftest test-arrow-function
  (testing "Function chaining with =>"
    (let [next-fn (fn [result] (assoc result :processed true))]
      (is (= (=> {:data "some data"} next-fn)
             {:data "some data" :processed true})
          "When there are no errors, next-fn should process the result")
      (is (= (=> {:errors [:some-error]} next-fn)
             {:errors [:some-error]})
          "When errors are present, next-fn should not be called"))))

(deftest test-collect-result
  (testing "Collect result from map"
    (is (= (collect-result {:data "some data" :errors nil})
           "some data")
        "When there are no errors, collect-result should return the data")
    (is (= (collect-result {:data "some data" :errors [:some-error]})
           {:errors [:some-error]})
        "When errors are present, collect-result should return only the errors")))

(deftest test-error-schema
  (testing "Validation of ErrorSchema"
    (is (true? (m/validate ErrorSchema {:errors [:some-error]}))
        "ErrorSchema should validate a map with a vector of keywords under :errors")
    (is (false? (m/validate ErrorSchema {:errors "some-error"}))
        "ErrorSchema should fail validation if :errors is not a vector of keywords")
    (is (false? (m/validate ErrorSchema {:data "some data"}))
        "ErrorSchema should fail validation if :errors is missing or nil")))

(run-tests)
