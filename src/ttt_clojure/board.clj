(ns ttt-clojure.board)

(def available-mark " ")
(def x-mark "X")
(def o-mark "O")

(defn- filter-indexed [f coll]
  (filter f (map-indexed (fn [a b] [a b]) coll)))

(defn new-board []
  (vec (map
         (fn [_] available-mark)
         (range 9))))

(defn make-move [board location mark]
  (assoc board location mark))

(defn- is-available? [space]
  (= available-mark space))

(defn- is-occupied? [mark location]
  (= mark location))

(defn available-spaces [board]
  (vec (map first
       (filter-indexed
         (fn [[idx space]] (is-available? space))
         board))))
