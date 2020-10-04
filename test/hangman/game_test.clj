(ns hangman.game-test
  (:require [clojure.test :refer :all]
            [hangman.game :as hg]
            [schema.core :as s])
  (:import (clojure.lang ExceptionInfo)
           (java.io FileNotFoundException)))

(s/set-fn-validation! true)

(deftest test-get-secret-word
  (testing "It should throw AssertionError when passed in an empty list"
    (is (thrown? AssertionError (hg/get-secret-word []))))

  (testing "It should throw ExceptionInfo when a vector of integers is passed in"
    (is (thrown? ExceptionInfo (hg/get-secret-word [22 5 67]))))

  (testing "It should return a word when a vector of strings is passed in"
    (let [fruits ["banana" "guava" "strawberry" "apple" "avocado"]
          secret-word (hg/get-secret-word fruits)]
      (is (true? (contains? (set fruits) secret-word))))))

(deftest test-read-file
  (testing "It should throw FileNotFoundException when an invalid file path is passed in"
    (is (thrown? FileNotFoundException (hg/read-file "anything.txt"))))

  (testing "It should return a string when a valid file path is passed in"
    (let [data (hg/read-file "resources/fruits.txt")]
      (println )
      (is (string? data)))))






