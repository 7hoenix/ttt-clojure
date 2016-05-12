(ns ttt-clojure.ttt-rules)

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

(defn- check-seq [board potential-seq mark]
  (every? (set (map first
    (filter-indexed
       (fn [[idx location]] (is-occupied? mark location))
       board)))
          potential-seq))

(defn is-tie? [available-spaces]
  (= (count available-spaces) 0))

(defn- check-seqs [board mark]
  (some (fn [potential-seq] (check-seq board
                                       potential-seq
                                       mark))
        winning-seqs))

(defn game-is-over? [board available-spaces]
  (or (some #(check-seqs board %) player-marks)
      (is-tie? available-spaces)))
