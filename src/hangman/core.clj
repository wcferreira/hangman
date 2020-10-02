(ns hangman.core
  (:gen-class)
  (:require [hangman.game :as hg]
            [hangman.drawings :as hd]
            [clojure.string :as s]))

(defn -main
  "Entry point"
  []
  (let [string (hg/read-file "resources/fruits.txt")
        words (hg/string->vector string)
        word (hg/get-secret-word words)
        secret-word (s/lower-case word)]
  (hd/display-welcome-message)
  (hg/initialize-correct-guesses secret-word hg/correct-guesses)
  (hg/play secret-word hg/correct-guesses)))
