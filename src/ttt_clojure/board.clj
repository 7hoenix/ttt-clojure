(ns ttt-clojure.board)

(def available-mark " ")
(def x-mark "X")
(def winning-seqs [[0 1 2] [3 4 5] [6 7 8]
                   [0 3 6] [1 4 7] [2 5 8]
                   [0 4 8] [2 4 6]])

(defn- filter-indexed [f coll]
  (filter f (map-indexed (fn [a b] [a b]) coll)))

(defn new-board []
  (vec (map
         (fn [x] available-mark)
         (range 9))))

(defn make-move [board location mark]
  (assoc board location mark))

(defn available-spaces [board]
  (map first
       (filter-indexed
         (fn [[idx space]] (= space available-mark))
         board)))

(defn valid-move? [board location]
  (= (nth board location) available-mark))

(defn- sequence-wins [board potential-seq]
  (= potential-seq (map first
       (filter-indexed
         (fn [[idx space]] (= space x-mark))
         board))))

(defn game-over? [board]
  (some (fn [potential-seq] (sequence-wins board potential-seq)) winning-seqs))
