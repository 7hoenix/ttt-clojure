(ns ttt-clojure.board)

(def available-mark " ")
(def x-mark "X")

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
