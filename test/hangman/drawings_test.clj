(ns hangman.drawings-test
  (:require [clojure.test :refer :all]
            [hangman.drawings :as hd]
            [schema.core :as s])
  (:import (clojure.lang ExceptionInfo)))

(s/set-fn-validation! true)

(deftest test-display-welcome-message
  (let [expected (str "=========================================\n"
                      "====== Welcome to the Hangman Game ======\n"
                      "=========================================\n")]
    (is (= expected (hd/display-welcome-message)))))

(deftest test-draw-hangman
  (testing "It should throw ClassCastException when a parameter different from integer is passed in"
    (is (thrown? ExceptionInfo (hd/draw-hangman "234")))
    (is (thrown? ExceptionInfo (hd/draw-hangman 33.4)))
    (is (thrown? ExceptionInfo (hd/draw-hangman []))))

  (testing "It should throw AssertionError when a number less than or equal to zero or greater than 7 is passed in"
    (is (thrown? AssertionError (hd/draw-hangman 0)))
    (is (thrown? AssertionError (hd/draw-hangman -1)))
    (is (thrown? AssertionError (hd/draw-hangman 8))))

  (testing "It should return the head when 1 is passed in"
    (is (= (hd/draw-head) (hd/draw-hangman 1))))

  (testing "It should return the head and right arm when 2 is passed in"
    (is (= (hd/draw-right-arm) (hd/draw-hangman 2))))

  (testing "It should return head, right arm and chest when 3 is passed in"
    (is (= (hd/draw-chest) (hd/draw-hangman 3))))

  (testing "It should return head, right arm, chest and left arm when 4 is passed in"
    (is (= (hd/draw-left-arm) (hd/draw-hangman 4))))

  (testing "It should return head, right arm, chest, left arm and thorax when 5 is passed in"
    (is (= (hd/draw-thorax) (hd/draw-hangman 5))))

  (testing "It should return head, right arm, chest, left arm, thorax and right leg when 6 is passed in"
    (is (= (hd/draw-right-leg) (hd/draw-hangman 6))))

  (testing "It should return head, right arm, chest, left arm, thorax, right leg and left leg when 7 is passed in"
    (is (= (hd/draw-left-leg) (hd/draw-hangman 7)))))

(deftest test-draw-winner-message
  (let [expected (str "Congratulations, you won!\n"
                      "       ___________      \n"
                      "      '._==_==_=_.'     \n"
                      "      .-\\\\:      /-.  \n"
                      "     | (|:.     |) |    \n"
                      "      '-|:.     |-'     \n"
                      "        \\\\::.    /    \n"
                      "         '::. .'        \n"
                      "           ) (          \n"
                      "         _.' '._        \n"
                      "        '-------'       \n")]
    (is (= expected (hd/draw-winner-message "banana")))))

(deftest test-draw-loser-message
  (let [secret-word "guava"
        expected (str "Game Over!\n"
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
                 "       \\_______/          \n")]
    (is (= expected (hd/draw-loser-message secret-word)))))

(deftest test-display-final-message
  (testing "It should throw ExceptionInfo when parameters different from boolean and string respectively are passed in"
    (is (thrown? ExceptionInfo (hd/display-final-message [] 355)))
    (is (thrown? ExceptionInfo (hd/display-final-message false 33.5M)))
    (is (thrown? ExceptionInfo (hd/display-final-message {} "guava"))))

  (testing "It should return the loser message when false and the secret word are passed in"
    (let [secret-word "banana"]
      (is (= (hd/draw-loser-message secret-word) (hd/display-final-message false secret-word)))))

  (testing "It should return the winner message when true and the secret word are passed in"
    (let [secret-word "peach"]
      (is (= (hd/draw-winner-message secret-word) (hd/display-final-message true secret-word))))))
