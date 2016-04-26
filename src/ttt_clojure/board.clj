(ns ttt-clojure.board)

(defn spots-available [board]
  (filter #(= % :empty) false) board)

(defn- calc-spots ([] (calc-spots 3))
  ([rows] (range 1 (inc (* rows rows)))))

(defn new-board [rows]
  (let [initial-board {}
        spots (calc-spots rows)]
    (reduce (fn [board spot]
              (assoc board spot {:empty true}))
              initial-board
              spots)))
