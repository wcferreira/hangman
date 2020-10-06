(ns hangman.drawings-test
  (:require [clojure.test :refer :all]
            [hangman.drawings :as hd]))

(deftest test-display-welcome-message
  (let [expected (str "=========================================\n"
                      "====== Welcome to the Hangman Game ======\n"
                      "=========================================\n")]
    (is (= expected (hd/display-welcome-message)))))

