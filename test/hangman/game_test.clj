(ns hangman.game-test
  (:require [clojure.test :refer :all]
            [hangman.game :as hg]
            [schema.core :as s])
  (:import (clojure.lang ExceptionInfo)))

(s/set-fn-validation! true)

(deftest test-get-secret-word
  (testing "It should return AssertionError when passed in an empty list"
    (is (thrown? AssertionError (hg/get-secret-word []))))

  (testing "It should return ExceptionInfo when a vector of integers is passed in"
    (is (thrown? ExceptionInfo (hg/get-secret-word [22 5 67]))))

  (testing "It should return a word when a vector of strings is passed in"
    (let [fruits ["banana" "guava" "strawberry" "apple" "avocado"]
          secret-word (hg/get-secret-word fruits)]
      (is (true? (contains? (set fruits) secret-word))))))



