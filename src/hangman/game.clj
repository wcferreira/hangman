(ns hangman.game)

(def secret "clojure")
(def correct_guesses (atom []))
(def max-number-attempts 7)

(defn display-welcome-message
  "Displays game welcome message"
  []
  (println "=========================================")
  (println "====== Welcome to the Hangman Game ======")
  (println "========================================="))

(defn initialize-correct-guesses
  "Initialize with _ (underscore) a vector that will hold the correct guesses"
  [secret-word]
  (reset! correct_guesses (into [] (repeat (count secret-word) "_"))))

(defn ask-player-for-a-guess
  "Wait player to guess a letter"
  []
  (read-line))

(defn string->map
  "Convert a string into a map"
  [secret-word]
  (->> (seq secret-word)
       (mapv str)
       (zipmap (range (count secret-word)))))

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
  [guesses]
  (loop [cnt (count @correct_guesses) col guesses]
    (when-not (empty? col)
      (let [[key value] (first col)]
        (swap! correct_guesses assoc key value))
      (recur (dec cnt)
             (rest col)))))

(defn is-word-already-guessed?
  "Check if the secret word was already guessed"
  []
  (nil? (some #(= % "_") @correct_guesses)))

(defn play
  "Entry point"
  [secret-word]
  (display-welcome-message)
  (initialize-correct-guesses secret-word)
  (loop [attempts max-number-attempts errors 0]
    (println @correct_guesses)
    (if (or (= attempts 0) (is-word-already-guessed?))
      (println "errors:" errors)
      (do
        (println "Guess a letter:")
        (let [guess (ask-player-for-a-guess)
              corrects (find-letter guess secret-word)]
          (update-correct-guesses corrects)
          (recur (dec attempts)
                 (if (empty? corrects)
                   (inc errors)
                   errors)))))))

