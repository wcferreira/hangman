(ns hangman.game
  (:require [hangman.drawings :as d]
            [clojure.string :as str]
            [schema.core :as s]))

(def correct-guesses (atom []))
(def max-number-attempts 7)


(s/defn get-secret-word :- s/Str
  "Obtain one random word"
  [col :- [s/Str]]
  {:pre [(not (empty? col))]}
  (rand-nth col))

(defn read-file
  "Read data from file"
  [path]
  (slurp path))

(defn string->vector
  "Converts a string to a vector"
  [data]
  (clojure.string/split data #"\n"))

(s/defn initialize-correct-guesses :- [s/Str]
  "Initialize with _ (underscore) a vector that will hold the correct guesses"
  [secret-word :- s/Str]
  (into [] (repeat (count secret-word) "_")))

(s/defn change-letter-case :- s/Str
  "Change letter case"
  [guess :- s/Str]
  (str/lower-case guess))

(defn string->map
  "Convert a string into a map"
  [secret-word]
  {:pre [(string? secret-word)]}
  (->> (seq secret-word)
       (mapv str)
       (zipmap (range (count secret-word)))))

(defn find-letter
  "Check if there is (are) a (some) letter(s) contained in secret-word"
  [letter secret-word]
  {:pre [(string? letter) (string? secret-word)]}
  (let [col (string->map secret-word)]
    (reduce (fn [acc curr]
              (if (= (get curr 1) letter)
                (let [[k v] curr]
                  (assoc acc k v))
                acc)) {} (vec col))))

(defn update-correct-guesses
  "Update collection that holds the correct guesses"
  [guesses data]
  {:pre [(map? guesses) (and (vector? data) (> (count data) 0))]}
  (reduce (fn [acc curr]
            (let [[k v] curr]
              (assoc acc k v))) data (vec guesses)))

(defn is-word-already-guessed?
  "Check if the secret word was already guessed"
  [data]
  {:pre [(> (count data) 0)]}
  (nil? (some #(= % "_") data)))

(defn get-error
  "Check collection that holds number of correct guesses. If empty returns 1 0 otherwise"
  [col]
  (if (empty? col) 1 0))

(defn play
  "Start the game"
  [secret-word the-atom]
  (loop [attempts max-number-attempts errors 0]
    (println @the-atom)
    (println (d/draw-hangman errors))
    (let [status (is-word-already-guessed? @the-atom)]
      (if (or (= attempts 0) status)
        (do
          (println (d/display-final-message status secret-word))
          status)
        (do
          (println "Guess a letter:")
          (let [guess (change-letter-case (read-line))
                corrects (find-letter guess secret-word)
                add-error (get-error corrects)]
            (reset! the-atom (update-correct-guesses corrects @the-atom))
            (recur (- attempts add-error)
                   (+ add-error errors))))))))
