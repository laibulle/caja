(ns app.petit-plat.mobile.core
  (:require
   ["expo" :as ex]
   ["react-native" :as rn]
   [shadow.expo :as expo]
   [reagent.core :as r]
   [account.interface :refer [hello]]))


(defn root []
  [:> rn/Text {:style {:font-size 30}} "nibnu"])

(defn start
  {:dev/after-load true}
  []
  (expo/render-root (r/as-element  [root])))

(comment
  (start))