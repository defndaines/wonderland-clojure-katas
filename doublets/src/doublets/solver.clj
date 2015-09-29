(ns doublets.solver
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

;; For REPL
(require '[clojure.java.io :as io])
(require '[clojure.edn :as edn])
;; End For REPL

(def words (-> "words.edn"
               (io/resource)
               (slurp)
               (read-string)))
(defn off-by-one [word1 word2]
  (let [base (vec word1)]
    (= 1 (count (filter (comp not zero?)
                        (map compare base (vec word2)))))))

;; (check "head" "heal" words [])

(defn check [word safe-word dict acc]
  (if (= word safe-word)
    acc
    (if-let [candidates (filter #(off-by-one word %) dict)]
      (let [so-far (set acc)
            valid (filter so-far candidates)]
        (if (seq valid)
          (recur (first valid) safe-word dict (conj acc (first valid))))))))

;; (doublets "head" "teal")

(defn doublets [word1 word2]
  (if (= (count word1) (count word2))
    (let [dict (filter #(= (count word1) (count %)) words)]
      (check word1 word2 dict []))
    []))
