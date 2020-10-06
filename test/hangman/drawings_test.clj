(ns hangman.drawings-test
  (:require [clojure.test :refer :all]
            [hangman.drawings :as hd]))

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
