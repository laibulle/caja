(ns food.use-cases.create-recipe-use-case
  (:require
   [malli.core :as m]
   [food.domain.recipe :as d]
   [next.jdbc :as jdbc]
   [postgres-db.interface :as db]))

(def invalid-input :invalid-input)

(def CreateRecipeInput
  [:map
   [:member-id d/MemberId]
   [:name d/RecipeName]])

(defn validate-create-recipe-input [input]
  (m/validate CreateRecipeInput input))

(defn- validate-input [input]
  (if (validate-create-recipe-input (:data input))
    input
    {:errors [invalid-input]}))

(defn execute [input]
  (jdbc/with-transaction [tx @db/datasource]
    (-> {:data input :tx tx}
        (validate-input))))

(comment
  (def input {:member-id 1 :name "Test recipe"})
  (validate-create-recipe-input input)
  (execute input))