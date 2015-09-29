(ns card-game-war.game)

;; feel free to use these cards or use your own data structure
(def suits [:spade :club :diamond :heart])
(def ranks [2 3 4 5 6 7 8 9 10 :jack :queen :king :ace])
(def cards
  (for [suit suits
        rank ranks]
    [suit rank]))

(def index-of (memfn indexOf item))

(defn rank-of [[_suit rank]]
    (index-of ranks rank))

(defn suit-of [[suit _rank]]
    (index-of suits suit))

(defn card-value [card]
  ((juxt rank-of suit-of) card))

(defn play-round [player1-cards player2-cards]
  (let [[card-1 & hand-1-seq] player1-cards
        [card-2 & hand-2-seq] player2-cards
        hand-1 (vec hand-1-seq)
        hand-2 (vec hand-2-seq)]
    (case (compare (card-value card-1) (card-value card-2))
      1 [(-> hand-1 (conj card-1) (conj card-2))
         hand-2]
      -1 [hand-1
          (-> hand-2 (conj card-1) (conj card-2))])))

(defn play-game [player1-cards player2-cards]
  (loop [[player-1 player-2] [player1-cards player2-cards]]
    (if (every? seq [player-1 player-2])
      (recur (play-round player-1 player-2))
      [player-1 player-2])))
