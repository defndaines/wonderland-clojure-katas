(ns alphabet-cipher.coder)

(def alphabet (seq "abcdefghijklmnopqrstuvwxyz"))

(defn- from-letter [c]
  (take 26 (drop-while #(> (int c) (int %)) (cycle alphabet))))

(defn- map-keyword [word length]
  (take length (cycle word)))

(defn- to-code [[k m]]
  (let [p (- (int k) (int \a))]
    (nth (from-letter m) p)))

(defn- from-code [[k m]]
  (nth alphabet (.indexOf (into [] (from-letter k)) m)))

(def cypher
  (for [x alphabet y alphabet] [x y (to-code [x y])]))

(defn- from-cypher [[m c]]
  (let [[x y z] (first (filter
                         (fn [[x y z]] (and (= y m) (= z c)))
                         cypher))]
    x))

(defn- translate [tfun keyword message]
  (apply str
         (map tfun (map vector
                        (map-keyword keyword (count message))
                        (seq message)))))

(declare maybe-repetition)
(declare certain-repetition)

(defn- repetition
  ([s] (repetition s 1))
  ([s l]
   (if (> l (/ (count s) 2))
     (maybe-repetition s l)
     (certain-repetition s l))))

;; This fails on "ababb" ... need to validate tail.
(defn- certain-repetition [s l]
  (let [p (partition l (seq s))
        h (first p)]
    (if (every? #(= h %) p)
      (apply str h)
      (repetition s (inc l)))))

(defn- maybe-repetition [s l]
  (if (= (count s) l)
    s
    (let [[candidate prefix] (split-at l s)]
      (if (= (take (count prefix) candidate) prefix)
        (apply str candidate)
        (repetition s (inc l))))))

(defn encode [keyword message]
  (translate to-code keyword message))

(defn decode [keyword message]
  (translate from-code keyword message))

(defn decypher [cypher message]
  (repetition (translate from-cypher message cypher)))
