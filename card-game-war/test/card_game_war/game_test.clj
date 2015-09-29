(ns card-game-war.game-test
  (:require [clojure.test :refer :all]
            [card-game-war.game :refer :all]))


;; fill in tests for your game
(deftest test-play-round
  (testing "the highest rank wins the cards in the round"
    (is (= [[[:spade 3] [:spade 2]] []]
           (play-round [[:spade 3]] [[:spade 2]]))))

  (testing "queens are higher rank than jacks"
    (is (= [[[:spade :queen] [:spade :jack]] []]
           (play-round [[:spade :queen]] [[:spade :jack]]))))

  (testing "kings are higher rank than queens"
    (is (= [[[:spade :king] [:spade :queen]] []]
           (play-round [[:spade :king]] [[:spade :queen]]))))

  (testing "aces are higher rank than kings"
    (is (= [[[:spade :ace] [:spade :king]] []]
           (play-round [[:spade :ace]] [[:spade :king]]))))

  (testing "if the ranks are equal, clubs beat spades"
    (is (= [[[:club :ace] [:spade :ace]] []]
           (play-round [[:club :ace]] [[:spade :ace]]))))

  (testing "if the ranks are equal, diamonds beat clubs"
    (is (= [[[:diamond :ace] [:spade :ace]] []]
           (play-round [[:diamond :ace]] [[:spade :ace]]))))

  (testing "if the ranks are equal, hearts beat diamonds"
    (is (= [[[:heart :ace] [:diamond :ace]] []]
           (play-round [[:heart :ace]] [[:diamond :ace]])))))

;; Note: Cannot use a random deck. Some games are unwinnable.
;;       ... even with only two cards!
;;  For example:
;;    Player 1: [[:spade 2] [:heart 3]]
;;    Player 2: [[:club 4] [:diamond 3]] 
(deftest test-play-game
  (testing "the player loses when they run out of cards"
    (let [player1-cards [[:spade 2] [:heart 6]]
          player2-cards [[:club 4] [:diamond 7]]
          game-result (play-game player1-cards player2-cards)]
      (is (and
            (or (empty? (first game-result))
                (empty? (second game-result)))
            (not-every? empty? game-result))))))
