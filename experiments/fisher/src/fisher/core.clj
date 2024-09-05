(ns fisher.core
  (:require [bosquet.llm.generator :refer [generate llm]]))

(comment
  (generate "When I was 6 my sister was half my age. Now I’m 70 how old is my sister?"))