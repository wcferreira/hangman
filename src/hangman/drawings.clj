(ns hangman.drawings
  (:require [schema.core :as s]))

(defn display-welcome-message
  "Displays game welcome message"
  []
  (apply str ["=========================================\n"
              "====== Welcome to the Hangman Game ======\n"
              "=========================================\n"]))

(def draw-head
  (apply str ["  _______     \n"
              " |/      |    \n"
              " |      (_)   \n"
              " |            \n"
              " |            \n"
              " |            \n"
              " |            \n"
              "_|___         \n"]))

(def draw-right-arm
  (apply str ["  _______     \n"
              " |/      |    \n"
              " |      (_)   \n"
              " |      \\    \n"
              " |            \n"
              " |            \n"
              " |            \n"
              "_|___         \n"]))

(def draw-chest
  (apply str ["  _______     \n"
              " |/      |    \n"
              " |      (_)   \n"
              " |      \\|   \n"
              " |            \n"
              " |            \n"
              " |            \n"
              "_|___         \n"]))


(def draw-left-arm
  (apply str ["  _______     \n"
              " |/      |    \n"
              " |      (_)   \n"
              " |      \\|/  \n"
              " |            \n"
              " |            \n"
              " |            \n"
              "_|___         \n"]))

(def draw-thorax
  (apply str ["  _______     \n"
              " |/      |    \n"
              " |      (_)   \n"
              " |      \\|/  \n"
              " |       |    \n"
              " |            \n"
              " |            \n"
              "_|___         \n"]))

(def draw-right-leg
  (apply str ["  _______     \n"
              " |/      |    \n"
              " |      (_)   \n"
              " |      \\|/  \n"
              " |       |    \n"
              " |      /     \n"
              " |            \n"
              "_|___         \n"]))

(def draw-left-leg
  (apply str ["  _______     \n"
              " |/      |    \n"
              " |      (_)   \n"
              " |      \\|/  \n"
              " |       |    \n"
              " |      / \\  \n"
              " |            \n"
              "_|___         \n"]))


(s/defn draw-hangman :- s/Str
  "Draw hangman according to the number of errors."
  [errors :- s/Int]
  {:pre [(and (>= errors 0) (<= errors 7))]}

  (let [error-types {0 ""
                     1 draw-head
                     2 draw-right-arm
                     3 draw-chest
                     4 draw-left-arm
                     5 draw-thorax
                     6 draw-right-leg
                     7 draw-left-leg}]
    (get error-types errors)))

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


(s/defn display-final-message :- s/Str
  [key :- s/Bool secret-word :- s/Str]
  (let [final-message-type {false (fn [secret-word] (draw-loser-message secret-word))
                            true  (fn [secret-word] (draw-winner-message secret-word))}]
    ((get (find final-message-type key) 1) secret-word)))
