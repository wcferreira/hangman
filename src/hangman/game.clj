(ns hangman.game)

(def correct_guesses (atom []))
(def max-number-attempts 7)

(defn get-secret-word
  []
  (let [string (slurp "resources/fruits.txt")
        fruits (clojure.string/split string #"\n")]
    (get fruits (rand-int (count fruits)))))

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

(defn get-error
  "Check collection that holds number of correct guesses. If empty returns 1 0 otherwise"
  [col]
  (if (empty? col) 1 0))


(defn draw-header
  ""
  []
  (println "  _______     ")
  (println " |/      |    "))

(defn draw-head
  []
  (println " |      (_)   ")
  (println " |            ")
  (println " |            ")
  (println " |            "))

(defn draw-right-arm
  []
  (println " |      (_)   ")
  (println " |      \\    ")
  (println " |            ")
  (println " |            "))

(defn draw-chest
  []
  (println " |      (_)   ")
  (println " |      \\|   ")
  (println " |            ")
  (println " |            "))

(defn draw-left-arm
  []
  (println " |      (_)   ")
  (println " |      \\|/  ")
  (println " |            ")
  (println " |            "))

(defn draw-torax
  []
  (println " |      (_)   ")
  (println " |      \\|/  ")
  (println " |       |    ")
  (println " |            "))

(defn draw-right-leg
  []
  (println " |      (_)   ")
  (println " |      \\|/  ")
  (println " |       |    ")
  (println " |      /     "))

(defn draw-left-leg
  []
  (println " |      (_)   ")
  (println " |      \\|/  ")
  (println " |       |    ")
  (println " |      / \\  "))

(defn draw-footer
  []
  (println " |            ")
  (println "_|___         ")
  (println))

(def error-types {1 (fn [] (draw-head))
                  2 (fn [] (draw-right-arm))
                  3 (fn [] (draw-chest))
                  4 (fn [] (draw-left-arm))
                  5 (fn [] (draw-torax))
                  6 (fn [] (draw-right-leg))
                  7 (fn [] (draw-left-leg))})

(defn draw-hangman
  "Draw hangman according to the number of errors."
  [errors]
  (println "==> " errors)
  (when (and (> errors 0) (<= errors max-number-attempts))
    (draw-header)
    ((get (find error-types errors) 1))
    (draw-footer)))

(defn draw-winner-message
  [_]
  (println "Congratulations, you won!")
  (println "       ___________      ")
  (println "      '._==_==_=_.'     ")
  (println "      .-\\\\:      /-.  ")
  (println "     | (|:.     |) |    ")
  (println "      '-|:.     |-'     ")
  (println "        \\\\::.    /    ")
  (println "         '::. .'        ")
  (println "           ) (          ")
  (println "         _.' '._        ")
  (println "        '-------'       "))

(defn draw-loser-message
  [secre-word]
  (println "Game Over!")
  (println "The secret word was:" secre-word)
  (println "    _______________       ")
  (println "   /               \\\\      ")
  (println "  /                 \\\\     ")
  (println "//                   \\/\\  ")
  (println "\\|   XXXX     XXXX   | /  ")
  (println " |   XXXX     XXXX   |/   ")
  (println " |   XXX       XXX   |    ")
  (println " |                   |    ")
  (println " \\__      XXX      __/    ")
  (println "   |\\     XXX     /|      ")
  (println "   | |           | |      ")
  (println "   | I I I I I I I |      ")
  (println "   |  I I I I I I  |      ")
  (println "   \\_             _/      ")
  (println "     \\_         _/        ")
  (println "       \\_______/          "))

(def final-message-type {false (fn [secret-word] (draw-loser-message secret-word))
                         true  (fn [secret-word] (draw-winner-message secret-word))})

(defn display-final-message
  [key secret-word]
  ((get (find final-message-type key) 1) secret-word))


(defn play
  "Entry point"
  []
  (let [secret-word (get-secret-word)]
    (display-welcome-message)
    (initialize-correct-guesses secret-word)
    (loop [attempts max-number-attempts errors 0]
      (println @correct_guesses)
      (draw-hangman errors)
      (let [status (is-word-already-guessed?)]
      (if (or (= attempts 0) status)
        (do
          (display-final-message status secret-word)
          errors)
        (do
          (println "Guess a letter:")
          (let [guess (ask-player-for-a-guess)
                corrects (find-letter guess secret-word)
                add-error (get-error corrects)]
            (update-correct-guesses corrects)
            (recur (dec attempts)
                   (+ add-error errors)))))))))



