(ns hangman.game
  (:require [hangman.drawings :as d]
            [clojure.string :as str]
            [schema.core :as s]))

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

(s/defn string->map :- {s/Num s/Str}
  "Convert a string into a map"
  [secret-word :- s/Str]
  (->> (seq secret-word)
       (mapv str)
       (zipmap (range (count secret-word)))))

(s/defn find-letter :- {s/Num s/Str}
  "Check if there is (are) a (some) letter(s) contained in secret-word"
  [letter :- s/Str secret-word :- s/Str]
  (let [col (string->map secret-word)]
    (reduce (fn [acc curr]
              (if (= (get curr 1) letter)
                (let [[k v] curr]
                  (assoc acc k v))
                acc)) {} (vec col))))

(defn update-correct-guesses
  "Update collection that holds the correct guesses"
  [guesses data]
  {:pre [(map? guesses) (and (vector? data) (not (empty? data)))]}
  (reduce (fn [acc curr]
            (let [[k v] curr]
              (assoc acc k v))) data (vec guesses)))

(s/defn is-word-already-guessed? :- s/Bool
  "Check if the secret word was already guessed"
  [data :- [s/Str]]
  {:pre [(not (empty? data))]}
  (nil? (some #(= % "_") data)))

(defn increment-number-of-errors-by
  "Check collection that holds number of correct guesses. If empty returns 1 0 otherwise"
  [col]
  (if (empty? col) 1 0))

(defprotocol GameProtocol
  (input [this] "Asks for user input")
  (output [this text] "Shows text to the user"))

(defrecord GameProd []
  GameProtocol
  (input [this]
    (read-line))
  (output [this text]
    (println text)))

(defrecord GameTest [ins outs]
  GameProtocol
  (input [this]
    (let [out (last @ins)]
      (swap! ins pop)
      out))
  (output [this text]
    (println text)
    (swap! outs conj text)))

(defn play
  "Start the game"
  [secret-word game]
  (loop [attempts 7 errors 0 result (initialize-correct-guesses secret-word)]
    (output game result)
    (output game (d/draw-hangman errors))
    (let [status (is-word-already-guessed? result)]
      (if (or (= attempts 0) status)
        (do
          (output game (d/display-final-message status secret-word))
          status)
        (do
          (output game "Guess a letter:")
          (let [guess (change-letter-case (input game))
                corrects (find-letter guess secret-word)
                add-error (increment-number-of-errors-by corrects)]
            (recur (- attempts add-error)
                   (+ add-error errors)
                   (update-correct-guesses corrects result))))))))
