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


