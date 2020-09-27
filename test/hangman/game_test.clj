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

(deftest test-find-letter
  (let [secret-word "guava"]
    (testing "When passed in a letter to be found. It returns a hash-map with the position of all the letters found"
      (is (= (hg/find-letter "a" secret-word) {2 "a" 4 "a"}))
      (is (= (hg/find-letter "u" secret-word) {1 "u"}))
      (is (= (hg/find-letter "g" secret-word) {0 "g"}))
      (is (= (hg/find-letter "v" secret-word) {3 "v"})))
    (testing "When passed in parameters different from string, returns nil"
      (is (nil? (hg/find-letter 1 secret-word)))
      (is (nil? (hg/find-letter "a" 3))))
      (is (nil? (hg/find-letter 4 5)))))

(deftest test-update-correct-guesses
  (let [secret-word "avocado"
        the-atom (atom [])]
    (hg/initialize-correct-guesses secret-word the-atom)
    (testing "It should return an atom with the (a) letter (s) initialized in its correct position"
      (is (= (hg/update-correct-guesses {} the-atom) ["_" "_" "_" "_" "_" "_" "_"]))

      (let [guesses {0 "a" 4 "a"}]
        (is (= (hg/update-correct-guesses guesses the-atom) ["a" "_" "_" "_" "a" "_" "_"])))

      (let [guesses {2 "o" 6 "o"}]
        (is (=  (hg/update-correct-guesses guesses the-atom) ["a" "_" "o" "_" "a" "_" "o"])))

      (let [guesses {1 "v"}]
        (is (= (hg/update-correct-guesses guesses the-atom) ["a" "v" "o" "_" "a" "_" "o"])))

      (let [guesses {3 "c"}]
        (is (= (hg/update-correct-guesses guesses the-atom) ["a" "v" "o" "c" "a" "_" "o"])))

      (let [guesses {5 "d"}]
        (is (= (hg/update-correct-guesses guesses the-atom) ["a" "v" "o" "c" "a" "d" "o"]))))

    (testing "It should return nil if the parameters passed in are a hash-map and a vector respectively"
      (is (nil? (hg/update-correct-guesses {} []))))

    (testing "It should return nil if the parameters passed in are a string and a hash-map"
      (is (nil? (hg/update-correct-guesses "foo" {}))))))

