(ns hangman.drawings)

(defn display-welcome-message
  "Displays game welcome message"
  []
  (apply str ["=========================================\n"
              "====== Welcome to the Hangman Game ======\n"
              "=========================================\n"]))

(defn draw-head
  []
  (apply str ["  _______     \n"
              " |/      |    \n"
              " |      (_)   \n"
              " |            \n"
              " |            \n"
              " |            \n"
              " |            \n"
              "_|___         \n"]))

(defn draw-right-arm
  []
  (apply str ["  _______     \n"
              " |/      |    \n"
              " |      (_)   \n"
              " |      \\    \n"
              " |            \n"
              " |            \n"
              " |            \n"
              "_|___         \n"]))

(defn draw-chest
  []
  (apply str ["  _______     \n"
              " |/      |    \n"
              " |      (_)   \n"
              " |      \\|   \n"
              " |            \n"
              " |            \n"
              " |            \n"
              "_|___         \n"]))


(defn draw-left-arm
  []
  (apply str ["  _______     \n"
              " |/      |    \n"
              " |      (_)   \n"
              " |      \\|/  \n"
              " |            \n"
              " |            \n"
              " |            \n"
              "_|___         \n"]))

(defn draw-torax
  []
  (apply str ["  _______     \n"
              " |/      |    \n"
              " |      (_)   \n"
              " |      \\|/  \n"
              " |       |    \n"
              " |            \n"
              " |            \n"
              "_|___         \n"]))

(defn draw-right-leg
  []
  (apply str ["  _______     \n"
              " |/      |    \n"
              " |      (_)   \n"
              " |      \\|/  \n"
              " |       |    \n"
              " |      /     \n"
              " |            \n"
              "_|___         \n"]))

(defn draw-left-leg
  []
  (apply str ["  _______     \n"
              " |/      |    \n"
              " |      (_)   \n"
              " |      \\|/  \n"
              " |       |    \n"
              " |      / \\  \n"
              " |            \n"
              "_|___         \n"]))


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
  (when (and (> errors 0) (<= errors (count error-types)))
    ((get (find error-types errors) 1))))

(defn draw-winner-message
  [_]
  (apply str ["Congratulations, you won!\n"
              "       ___________      \n"
              "      '._==_==_=_.'     \n"
              "      .-\\\\:      /-.  \n"
              "     | (|:.     |) |    \n"
              "      '-|:.     |-'     \n"
              "        \\\\::.    /    \n"
              "         '::. .'        \n"
              "           ) (          \n"
              "         _.' '._        \n"
              "        '-------'       \n"]))

(defn draw-loser-message
  [secret-word]
  (apply str ["Game Over!\n"
              (str "The secret word was:" secret-word) "\n"
              "    _______________        \n"
              "   /               \\\\    \n"
              "  /                 \\\\   \n"
              "//                   \\/\\ \n"
              "\\|   XXXX     XXXX   | /  \n"
              " |   XXXX     XXXX   |/    \n"
              " |   XXX       XXX   |     \n"
              " |                   |     \n"
              " \\__      XXX      __/    \n"
              "   |\\     XXX     /|      \n"
              "   | |           | |       \n"
              "   | I I I I I I I |       \n"
              "   |  I I I I I I  |       \n"
              "   \\_             _/      \n"
              "     \\_         _/        \n"
              "       \\_______/          \n"]))

(def final-message-type {false (fn [secret-word] (draw-loser-message secret-word))
                         true  (fn [secret-word] (draw-winner-message secret-word))})

(defn display-final-message
  [key secret-word]
  ((get (find final-message-type key) 1) secret-word))
