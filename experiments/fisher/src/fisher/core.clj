(ns fisher.core
  (:require
   [bosquet.llm.wkk :as k]
   [bosquet.llm.generator :refer [generate llm]]))

(comment
  (generate
   {:question-answer "Question: {{question}}  Answer: {{answer}}"
    :answer          (llm :ollama k/model-params {:model "llama3.1"})
    :self-eval       ["{{question-answer}}"
                      ""
                      "Is this a correct answer? {{test}}"]
    :test            (llm :ollama k/model-params {:model "llama3.1"})}
   {:question "What is the distance from Moon to Io?"}))