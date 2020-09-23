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

(defn string->map
  "Convert a string into a map"
  [secret-word]
  (->> (seq secret-word)
       (mapv str)
       (zipmap (range 1 (+ (count secret-word) 1)))))

(defn find-letter [letter secret-word]
  "Check if there is (are) a (some) letter(s) in col"
  (let [col (string->map secret-word)]
    (reduce (fn [acc curr]
              (if (= (get curr 1) letter)
                (let [[k v] curr]
                  (assoc acc k v))
                acc)) {} (vec col))))

(defn play
  "Entry point"
  []
  (display-welcome-message)
  (initialize-correct-guesses secret)
  (ask-player-for-a-guess))



