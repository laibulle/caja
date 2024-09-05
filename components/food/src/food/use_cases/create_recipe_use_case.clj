(ns food.use-cases.create-recipe-use-case
  (:require
   [malli.core :as m]
   [food.domain.recipe :as d]))

(def CreateRecipeInput
  [:map
   [:member-id d/MemberId]
   [:name d/RecipeName]])

(defn validate-create-recipe-input [input]
  (m/validate CreateRecipeInput input))

(defn execute [input])

(comment
  (validate-create-recipe-input {:member-id 1 :name "Test recipe"}))