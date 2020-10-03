(ns hangman.game
  (:require [hangman.drawings :as d]
            [clojure.string :as s]
            [clojure.string :as str])
  (:import (java.io FileNotFoundException)))

(def correct-guesses (atom []))
(def max-number-attempts 7)

(defn get-secret-word
  [col]
  (get col (rand-int (count col))))

(defn read-file
  [path]
   (try
     (slurp path)
     (catch FileNotFoundException e (println "Invalid path"))))

(defn string->vector
  [data]
  (clojure.string/split data #"\n"))

(defn atom?
  [the-atom]
  (instance? clojure.lang.Atom the-atom))

(defn initialize-correct-guesses
  "Initialize with _ (underscore) a vector that will hold the correct guesses"
  [secret-word]
  {:pre [(string? secret-word)]}
  (into [] (repeat (count secret-word) "_")))

(defn ask-player-for-a-guess
  "Wait player to guess a letter"
  [guess]
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
  [guesses the-atom]
  (when (and (map? guesses) (atom? the-atom))
    (loop [cnt (count @the-atom) col guesses]
      (if (empty? col)
        @the-atom
        (do
          (let [[key value] (first col)]
            (swap! the-atom assoc key value))
          (recur (dec cnt)
                 (rest col)))))))

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
          (let [guess (ask-player-for-a-guess (read-line))
                corrects (find-letter guess secret-word)
                add-error (get-error corrects)]
            (update-correct-guesses corrects the-atom)
            (recur (dec attempts)
                   (+ add-error errors))))))))

