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
(defn HomeScreen [^js props]
  [:> rn/View
   [:> rn/Text "Home Screen"]
   [:> rn/Button {:title "Go to details" :on-press (fn []
                                                     (.navigate (.-navigation props) "details"))}]])

(defn DetailsScreen [^js props]
  [:> rn/View
   [:> rn/Text "Details Screen"]])

(defn StackNavigator []
  (let [StackNavigator (.-Navigator stack)
        StackScreen (.-Screen stack)]
    [:> NavigationContainer
     [:> StackNavigator
      [:> StackScreen {:name "home"}
       (fn [^js props]
         (r/as-element [HomeScreen props]))]
      [:> StackScreen {:name "details"}
       (fn [^js props]
         (r/as-element [DetailsScreen props]))]]]))

(defn start
  {:dev/after-load true}
  []
  (expo/render-root (r/as-element [StackNavigator])))

(comment
  (start))
