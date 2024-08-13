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

(defn home-screen []
  (fn []
    [:> rn/View
     [:> rn/Text "Home Screen"]]))  ; Use a string instead of a keyword

(defn details-screen []
  (fn []
    [:> rn/View
     [:> rn/Text "Details Screen"]]))

(defn stack-navigator []
  (let [StackNavigator (.-Navigator stack)
        StackScreen (.-Screen stack)]
    [:> NavigationContainer
     [:> StackNavigator
      [:> StackScreen {:name "Home" :component home-screen}]
      [:> StackScreen {:name "Details" :component details-screen}]]]))

(defn start
  {:dev/after-load true}
  []
  (expo/render-root (r/as-element [stack-navigator])))

(comment
  (start))