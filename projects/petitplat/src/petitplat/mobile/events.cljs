(ns petitplat.mobile.events
  (:require
   [petitplat.mobile.infrastructure.local-storage :as ls]
   [re-frame.alpha :refer [reg-event-db reg-fx reg-event-fx inject-cofx path after sub]]
   [cljs.spec.alpha :as s]))

(reg-fx
 :load-storage
 (fn [key]
   (ls/get-item (key))))

(reg-event-fx              ;; -fx registration, not -db registration
 :initialize
 (fn [_ _]
   {:load-storage "petit-plat"}))

(reg-event-db
 :storage-loaded
 (fn [db [_ key value]]
   (assoc-in db [:storage key] value)))