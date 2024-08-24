(ns petitplat.mobile.screens.login-screen
  (:require
   ["expo" :as ex]
   ["expo-apple-authentication" :as AppleAuthentication]
   ["react-native" :as rn]
   ["@react-navigation/native" :refer [NavigationContainer]]
   ["@react-navigation/stack" :refer [createStackNavigator]]
   [shadow.expo :as expo]
   [reagent.core :as r]
   [account.interface :refer [hello]]))

(defn LoginScreen [^js props]
  [:> rn/View
   [:> rn/Text "Login"]
   [:> AppleAuthentication/AppleAuthentication]
   [:> rn/Button {:title "Sign In" :on-press (fn [])}]])
