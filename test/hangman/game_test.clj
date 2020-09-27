(ns hangman.game-test
  (:require [clojure.test :refer :all]
            [hangman.game :as hg]))


(deftest test-get-secret-word
  (testing "It should return a secret word when list is not empty"
    (let [fruits ["banana" "guava" "strawberry" "apple" "avocado"]
          secret-word (hg/get-secret-word fruits)]
      (is (true? (contains? (set fruits) secret-word)))))
  (testing "It should return nil when list is empty"
    (let [fruits []
          secret-word (hg/get-secret-word fruits)]
      (is (nil? secret-word)))))

(deftest test-initialize-correct-guess
  (testing "It should return a vector of underscores when initialized with a string (secret-word) and an atom"
    (let [the-atom (atom [])
          secret-word "banana"]
      (is (= (hg/initialize-correct-guesses secret-word the-atom) ["_" "_" "_" "_" "_" "_"]))))
  (testing "It should return an empty vector when initialized with a blank string and an atom"
    (let [the-atom (atom [])
          secret-word ""]
      (is (= (hg/initialize-correct-guesses secret-word the-atom) []))))
  (testing "It should return nil when initialized with a number and an atom"
    (let [the-atom (atom [])
          secret-word 123]
      (is (nil? (hg/initialize-correct-guesses secret-word the-atom)))))
  (testing "It should return nil when initialized with a string and a vector"
    (let [the-vec []
          secret-word "peach"]
      (is (nil? (hg/initialize-correct-guesses secret-word the-vec))))))

(deftest test-string-to-map
  (testing "It should return a map when a string is passed in"
    (is (= (hg/string->map "apple") {0 "a" 1 "p" 2 "p" 3 "l" 4 "e"})))
  (testing "It should return nil when a parameter different of string is passed in"
    (is (nil? (hg/string->map 42))))
    (is (nil? (hg/string->map [])))
    (is (nil? (hg/string->map :banana))))

