(ns petitplat.mobile.subs
  (:require [re-frame.alpha :refer [reg-sub subscribe reg]]))

(reg-sub
 :color          ;; usage:   (subscribe [:showing])
 (fn [db _]        ;; db is the (map) value stored in the app-db atom
   (:time-color db)))