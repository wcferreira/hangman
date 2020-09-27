(ns hangman.core
  (:gen-class)
  (:require [hangman.game :as hg]))

(defn -main
  []
  (hg/play))
