(ns petit-plat.mobile.core
  (:require
   ["expo" :as ex]
   ["react-native" :as rn]
   ["@react-navigation/native" :refer [NavigationContainer]]
   ["@react-navigation/stack" :refer [createStackNavigator]]
   [shadow.expo :as expo]
   [reagent.core :as r]
   [account.interface :refer [hello]]))

(def stack (createStackNavigator))

;; Define your screen components
(defn HomeScreen []
  [:> rn/View
   [:> rn/Text "Home Screen"]])

(defn DetailsScreen []
  [:> rn/View
   [:> rn/Text "Details Screen"]])

(defn StackNavigator []
  (let [StackNavigator (.-Navigator stack)
        StackScreen (.-Screen stack)]
    [:> NavigationContainer
     [:> StackNavigator
      ;; Passing the component as a child function
      [:> StackScreen {:name "Home"}
       (fn []
         (r/as-element [HomeScreen]))]
      [:> StackScreen {:name "Details"}
       (fn []
         (r/as-element [DetailsScreen]))]]]))

(defn start
  {:dev/after-load true}
  []
  (expo/render-root (r/as-element [StackNavigator])))

(comment
  (start))
