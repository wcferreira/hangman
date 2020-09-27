(ns hangman.game
  (:require [hangman.drawings :as d]))

(def correct_guesses (atom []))
(def max-number-attempts 7)

(defn get-secret-word
  [col]
  (get col (rand-int (count col))))

(defn read-words-from-file
  []
  (let [string (slurp "resources/fruits.txt")
        fruits (clojure.string/split string #"\n")]
    fruits))

(defn atom?
  [the-atom]
  (instance? clojure.lang.Atom the-atom))

(defn initialize-correct-guesses
  "Initialize with _ (underscore) a vector that will hold the correct guesses"
  [secret-word the-atom]
  (when (and (string? secret-word) (atom? the-atom))
    (reset! the-atom (into [] (repeat (count secret-word) "_")))))

(defn ask-player-for-a-guess
  "Wait player to guess a letter"
  []
  (read-line))

(defn string->map
  "Convert a string into a map"
  [secret-word]
  (when (string? secret-word)
    (->> (seq secret-word)
         (mapv str)
         (zipmap (range (count secret-word))))))

(defn find-letter
  "Check if there is (are) a (some) letter(s) contained in secret-word"
  [letter secret-word]
  (let [col (string->map secret-word)]
    (reduce (fn [acc curr]
              (if (= (get curr 1) letter)
                (let [[k v] curr]
                  (assoc acc k v))
                acc)) {} (vec col))))

(defn update-correct-guesses
  "Update collection that holds the correct guesses"
  [guesses the-atom]
  (loop [cnt (count @the-atom) col guesses]
    (when-not (empty? col)
      (let [[key value] (first col)]
        (swap! the-atom assoc key value))
      (recur (dec cnt)
             (rest col)))))

(defn is-word-already-guessed?
  "Check if the secret word was already guessed"
  []
  (nil? (some #(= % "_") @correct_guesses)))

(defn get-error
  "Check collection that holds number of correct guesses. If empty returns 1 0 otherwise"
  [col]
  (if (empty? col) 1 0))

(defn play
  "Entry point"
  []
  (let [words (read-words-from-file)
        secret-word (get-secret-word words)]
    (d/display-welcome-message)
    (initialize-correct-guesses secret-word)
    (loop [attempts max-number-attempts errors 0]
      (println @correct_guesses)
      (d/draw-hangman errors)
      (let [status (is-word-already-guessed?)]
      (if (or (= attempts 0) status)
        (do
          (d/display-final-message status secret-word)
          errors)
        (do
          (println "Guess a letter:")
          (let [guess (ask-player-for-a-guess)
                corrects (find-letter guess secret-word)
                add-error (get-error corrects)]
            (update-correct-guesses corrects)
            (recur (dec attempts)
                   (+ add-error errors)))))))))



