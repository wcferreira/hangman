(ns hangman.core
  (:gen-class)
  (:require [hangman.game :as hg]
            [hangman.drawings :as hd]))

(defn -main
  "Entry point"
  []
  (let [words (hg/read-words-from-file)
        secret-word (hg/get-secret-word words)]
  (hd/display-welcome-message)
  (hg/initialize-correct-guesses secret-word hg/correct_guesses)
  (hg/play secret-word hg/correct_guesses)))
