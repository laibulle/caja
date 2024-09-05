(ns food.use-cases.create-recipe-use-case
  (:require [malli.core :as m]))

(def CreateRecipeInput
  [:map
   [:member-id :int]
   [:name :string]])

(defn validate-create-recipe-input [input]
  (m/validate CreateRecipeInput input))

(defn execute [input])

(comment
  (validate-create-recipe-input {:member-id 1 :name "Test recipe"}))