(ns ttt-clojure.board)

(def available-mark " ")
(def x-mark "X")
(def winning-seqs [0 1 2])

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

(defn game-over? [board]
  (= winning-seqs (map first
       (filter-indexed
         (fn [[idx space]] (= space x-mark))
         board))))
