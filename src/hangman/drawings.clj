(ns hangman.drawings)

(defn display-welcome-message
  "Displays game welcome message"
  []
  (println "=========================================")
  (println "====== Welcome to the Hangman Game ======")
  (println "========================================="))

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
  (when (and (> errors 0) (<= errors (count error-types)))
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
