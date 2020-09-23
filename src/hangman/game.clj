(ns hangman.game)

(def secret "clojure")
(def correct_chars (atom []))

(defn display-welcome-message
  "Displays game welcome message"
  []
  (println "=========================================")
  (println "====== Welcome to the Hangman Game ======")
  (println "========================================="))

(defn initialize-correct-guesses
  "Initialize with _ (underscore) a vector that will hold the correct guesses"
  [secret-word]
  (reset! correct_chars (into [] (repeat (count secret-word) "_"))))

(defn ask-player-for-a-guess
  "Wait player to guess a letter"
  []
  (print "Guess a letter: ")
  (read-line))

(defn play
  "Entry point"
  []
  (display-welcome-message)
  (initialize-correct-guesses secret)
  (ask-player-for-a-guess))


