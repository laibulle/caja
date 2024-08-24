(ns petitplat.mobile.screens.login-screen
  (:require
   ["expo" :as ex]
   ["react-native" :as rn]
   ["@react-navigation/native" :refer [NavigationContainer]]
   ["@react-navigation/stack" :refer [createStackNavigator]]
   [shadow.expo :as expo]
   [reagent.core :as r]
   [account.interface :refer [hello]]))

(defn LoginScreen [^js props]
  [:> rn/View
   [:> rn/Text "Login"]
   [:> rn/Button {:title "Sign In" :on-press (fn [])}]])
