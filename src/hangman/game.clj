(ns hangman.game)

(def secret "clojure")
(def correct_chars (atom (into [] (repeat (count secret) "_"))))

(defn display-welcome-message
  []
  (println "=========================================")
  (println "====== Welcome to the Hangman Game ======")
  (println "========================================="))

(defn play
  []
  (display-welcome-message)
  (println @correct_chars)
  )