(ns app.petit-plat.authentication.interface-test
  (:require [clojure.test :refer :all]
            [app.petit-plat.authentication.interface :refer :all]
            [app.petit-plat.authentication.use-cases.authenticate-use-case :as authenticate-use-case]))

(deftest authenticate-calls-execute-test
  (testing "authenticate function should call authenticate-use-case/execute"
    (let [execute-called (atom false)
          mock-input {:email "test@example.com" :password "password"}]
      (with-redefs [authenticate-use-case/execute (fn [input]
                                                    (reset! execute-called true)
                                                    {:jwt "mocked-jwt-token"})]
        (authenticate mock-input)
        (is @execute-called "Expected execute function to be called")))))

(comment
  (run-all-tests))
