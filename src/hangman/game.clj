(ns hangman.game)

(def secret "clojure")
(def correct_chars (atom []))

(defn display-welcome-message
  []
  (println "=========================================")
  (println "====== Welcome to the Hangman Game ======")
  (println "========================================="))

(defn initialize-correct-guesses
  [secret-word]
  (reset! correct_chars (into [] (repeat (count secret-word) "_"))))

(defn play
  []
  (display-welcome-message)
  (initialize-correct-guesses secret))
