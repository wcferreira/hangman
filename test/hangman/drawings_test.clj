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

(deftest test-draw-head
  (let [expected (str "  _______     \n"
                      " |/      |    \n"
                      " |      (_)   \n"
                      " |            \n"
                      " |            \n"
                      " |            \n"
                      " |            \n"
                      "_|___         \n")]
    (is (= expected (hd/draw-head)))))

(deftest test-draw-right-arm
  (let [expected (str "  _______     \n"
                      " |/      |    \n"
                      " |      (_)   \n"
                      " |      \\    \n"
                      " |            \n"
                      " |            \n"
                      " |            \n"
                      "_|___         \n")]
    (is (= expected (hd/draw-right-arm)))))

(deftest test-draw-chest
  (let [expected (str "  _______     \n"
                      " |/      |    \n"
                      " |      (_)   \n"
                      " |      \\|   \n"
                      " |            \n"
                      " |            \n"
                      " |            \n"
                      "_|___         \n")]
    (is (= expected (hd/draw-chest)))))

(deftest test-draw-left-arm
  (let [expected (str "  _______     \n"
                      " |/      |    \n"
                      " |      (_)   \n"
                      " |      \\|/  \n"
                      " |            \n"
                      " |            \n"
                      " |            \n"
                      "_|___         \n")]
    (is (= expected (hd/draw-left-arm)))))

(deftest test-draw-thorax
  (let [expected (str "  _______     \n"
                      " |/      |    \n"
                      " |      (_)   \n"
                      " |      \\|/  \n"
                      " |       |    \n"
                      " |            \n"
                      " |            \n"
                      "_|___         \n")]
    (is (= expected (hd/draw-thorax)))))

(deftest test-draw-right-leg
  (let [expected (str "  _______     \n"
                      " |/      |    \n"
                      " |      (_)   \n"
                      " |      \\|/  \n"
                      " |       |    \n"
                      " |      /     \n"
                      " |            \n"
                      "_|___         \n")]
    (is (= expected (hd/draw-right-leg)))))

(deftest test-draw-left-leg
  (let [expected (str "  _______     \n"
                      " |/      |    \n"
                      " |      (_)   \n"
                      " |      \\|/  \n"
                      " |       |    \n"
                      " |      / \\  \n"
                      " |            \n"
                      "_|___         \n")]
    (is (= expected (hd/draw-left-leg)))))

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
