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
      (is (string? data)))))

(deftest test-string->vector
  (testing "It should throw ClassCastException when a number is passed in"
    (is (thrown? ClassCastException (hg/string->vector 455))))

  (testing "It should return a vector of strings when a string is passed in"
    (let [data "avocado\nguava\ncherry\npeach\nstrawberry"
          conv (hg/string->vector data)
          result (every? #(string? %) conv)]
      (is (true? result)))))

(deftest test-initialize-correct-guesses
  (testing "It should throw ExceptionInfo if anything different than a string is passed in"
    (is (thrown? ExceptionInfo (hg/initialize-correct-guesses 88))))

  (testing "It should return a vector of underscores if a string is passed in"
    (is (= ["_" "_" "_" "_" "_" "_"] (hg/initialize-correct-guesses "banana")))
    (is (= ["_" "_" "_"] (hg/initialize-correct-guesses "nda"))))

  (testing "It should return an empty vector when an empty string is passed in"
    (is (= [] (hg/initialize-correct-guesses "")))))

(deftest test-change-letter-case
  (testing "It should throw ExceptionInfo if anything other than a string is passed in"
    (is (thrown? ExceptionInfo (hg/change-letter-case 35))))

  (testing "It should return a lower case letter if a letter with upper or lower case is passed in"
    (is (= "w" (hg/change-letter-case "W")))
    (is (= "a" (hg/change-letter-case "a"))))

  (testing "It should return an empty string when a n empty string is passed in"
    (is (= "" (hg/change-letter-case "")))))

(deftest test-string->map
  (testing "It should throw ExceptionInfo if anything other than a string is passed in"
    (is (thrown? ExceptionInfo (hg/string->map 999))))

  (testing "It should return a hash-map when a string is passed in"
    (let [word "banana"
          expected {0 "b" 1 "a" 2 "n" 3 "a" 4 "n" 5 "a"}]
      (is (= expected (hg/string->map word)))))

  (testing "It should return an empty hash-map when an empty string is passed in"
    (is (= {} (hg/string->map "")))))

(deftest test-find-letter
  (testing "It should return Exception if the parameters passed in is different of string"
    (is (thrown? ExceptionInfo (hg/find-letter [] 677)))
    (is (thrown? ExceptionInfo (hg/find-letter 88 "banana")))
    (is (thrown? ExceptionInfo (hg/find-letter "a" {}))))

  (testing "It should return hash-map when a letter and a secret word is passed in"
    (let [letter "a"
          secret-word "banana"
          expected {1 "a" 3 "a" 5 "a"}]
      (is (= expected (hg/find-letter letter secret-word))))

    (let [letter "n"
          secret-word "banana"
          expected {2 "n" 4 "n"}]
      (is (= expected (hg/find-letter letter secret-word))))

    (let [letter "b"
          secret-word "banana"
          expected {0 "b"}]
      (is (= expected (hg/find-letter letter secret-word)))))

  (testing "It should return an empty hash-map if empty string is passed in"
    (is (= {} (hg/find-letter "" "banana")))
    (is (= {} (hg/find-letter "a" "")))
    (is (= {} (hg/find-letter "" "")))))

(deftest test-update-correct-guesses
  (testing "It should throw AssertionError when parameters are different than a hash-map and a vector respectively"
    (is (thrown? AssertionError (hg/update-correct-guesses 785 "")))
    (is (thrown? AssertionError (hg/update-correct-guesses {} []))))

  (testing "It should return a vector with the correct guesses"
    (let [secret-word "apple"
          data (hg/initialize-correct-guesses secret-word)
          guesses {1 "p" 2 "p"}
          expected ["_" "p" "p" "_" "_"]]
      (is (= expected (hg/update-correct-guesses guesses data))))

    (let [data ["_" "p" "p" "_" "_"]
          guesses {0 "a"}
          expected ["a" "p" "p" "_" "_"]]
      (is (= expected (hg/update-correct-guesses guesses data))))

    (let [data ["a" "p" "p" "_" "_"]
          guesses {3 "l"}
          expected ["a" "p" "p" "l" "_"]]
      (is (= expected (hg/update-correct-guesses guesses data))))

    (let [data ["a" "p" "p" "l" "_"]
          guesses {4 "e"}
          expected ["a" "p" "p" "l" "e"]]
      (is (= expected (hg/update-correct-guesses guesses data))))))






















