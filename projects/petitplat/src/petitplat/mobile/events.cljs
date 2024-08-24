(ns petitplat.mobile.events
  (:require [re-frame.alpha :refer [reg-event-db reg-event-fx inject-cofx path after sub]]
            [cljs.spec.alpha :as s]))

(reg-event-db              ;; sets up initial application state
 :initialize                 ;; usage:  (dispatch [:initialize])
 (fn [_ _]                   ;; the two parameters are not important here, so use _
   {:time (js/Date.)         ;; What it returns becomes the new application state
    :time-color "orange"}))  ;; so the application state will initially be a map with two keys