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

;; Define the components properly
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
      [:> StackScreen {:name "Home" :component (r/reactify-component HomeScreen)}]
      [:> StackScreen {:name "Details" :component (r/reactify-component DetailsScreen)}]]]))

(defn start
  {:dev/after-load true}
  []
  (expo/render-root (r/as-element [StackNavigator])))

(comment
  (start))
