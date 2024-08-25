(ns petitplat.mobile.test-runner
  (:require [cljs.test :refer-macros [run-all-tests]]
            [petitplat.mobile.infrastructure.local-storage-test])) ;; Add more test namespaces as needed

(enable-console-print!)

(run-all-tests #"petitplat.*-test")
