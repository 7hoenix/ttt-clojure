(ns ttt-clojure.ttt-rules
  (:require [ttt-clojure.board :as board]))

(def x-mark "X")
(def o-mark "O")
(def player-marks [x-mark o-mark])
(def winning-seqs [[0 1 2] [3 4 5] [6 7 8]
                   [0 3 6] [1 4 7] [2 5 8]
                   [0 4 8] [2 4 6]])

(defn- filter-indexed [f coll]
  (filter f (map-indexed (fn [a b] [a b]) coll)))

(defn- is-occupied? [mark location]
  (= mark location))

(defn- is-blank? [_ location]
  (= location " "))

(defn- check-seq [board potential-seq mark]
  (every? (set (map first
    (filter-indexed
       (fn [[idx location]] (is-occupied? mark location))
       board)))
          potential-seq))

(defn- count-idxs [board potential-seq mark count-func]
  (let [indexed (filter-indexed (fn [[idx location]] (count-func mark location))
                  board)
        matches (for [[idx location] indexed :when (some #(= idx %) potential-seq)] idx)]
    (count matches)))

(defn is-tie? [game]
  (= (count (board/available-spaces (:board game))) 0))

(defn- check-seqs [board mark]
  (some (fn [potential-seq] (check-seq board
                                       potential-seq
                                       mark))
        winning-seqs))

(defn- check-seq-advantage [board potential-seq mark]
  (and (= 2 (count-idxs board potential-seq mark is-occupied?))
       (= 1 (count-idxs board potential-seq mark is-blank?))))

(defn advantage-count [board mark]
  (count (filter true? (map #(check-seq-advantage board % mark) winning-seqs))))

(defn outcome [game]
  (let [board (:board game)]
    (cond
      (check-seqs board x-mark) x-mark
      (check-seqs board o-mark) o-mark
      (is-tie? game) false)))

(defn game-is-over? [game]
  (let [board (:board game)
        available (board/available-spaces board)]
    (or (some #(check-seqs board %) player-marks)
        (is-tie? game))))
