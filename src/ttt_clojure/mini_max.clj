(ns ttt-clojure.mini-max
  (:require [ttt-clojure.board :as board]
            [ttt-clojure.ttt-rules :as ttt]))

(declare minimax maxi)

(defn- game-over? [board]
  (ttt/game-is-over? board
                     (board/available-spaces board)))

(defn score [board current-player depth]
  (if (ttt/winner board)
    (if (= ttt/winner current-player)
      (- 10 depth)
      (+ depth -10))
    0))

(defn best-score [tree current-player]
  (reduce (fn [itm comparison]
            (if (empty? itm)
              comparison
              (if (< (second comparison) (second itm))
                itm
                comparison)))
          tree))

(defn- mm-algorithm [board current-player opponent-player location multiplier depth]
   (if (game-over? board)
     (vector location (* multiplier (score board current-player depth)))
     (let [available (board/available-spaces board)
           states (map (fn [spot]
                         (vector spot
                                (board/make-move board
                                                 spot
                                                 current-player)))
                       available)
           tree (reduce (fn [bb [location board]]
                          (conj bb (maxi board
                                         current-player
                                         opponent-player
                                         location
                                         multiplier
                                         depth)))
                        []
                        states)]
       (best-score tree current-player))))

(defn maxi [board current-player opponent-player location multiplier depth]
  (mm-algorithm board opponent-player current-player location (* -1 multiplier) (+ 1 depth)))

(defn minimax [board current-player opponent-player]
  (first (mm-algorithm board current-player opponent-player nil -1 0)))
