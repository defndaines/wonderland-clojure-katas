(ns tiny-maze.solver)

(defn solve-maze [maze])

;; (let [maze [[:S 0 1]
;;             [1  0 1]
;;             [1  0 :E]]
;;       sol [[:x :x 1]
;;            [1  :x 1]
;;            [1  :x :x]]]
;;   (is (= sol (solve-maze maze))))

(def maze [[:S 0 1]
           [1  0 1]
           [1  0 1]
           [1  0 :E]])

(defn position [alice maze]
  (for [[x row] (map-indexed vector maze)
        [y value] (map-indexed vector row)
        :when (= alice value)]
    [x y]))

(defn neighbors [position maze]
  (let [[x y] position
        max-y (count maze)
        max-x (apply max (map count maze))
        adjacent [[(inc x) y] [(dec x) y] [x (inc y)] [x (dec y)]]
        in-bounds (fn [[x y]] (and (every? #(> % -1) [x y]) (< x max-x) (< y max-y)))
        ]
    (filter in-bounds adjacent)))
