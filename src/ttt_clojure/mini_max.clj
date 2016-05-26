(ns ttt-clojure.mini-max
  (:require [ttt-clojure.board :as board]
            [ttt-clojure.ttt-rules :as ttt]
            [ttt-clojure.cli :as cli]))

(declare minimax maxi)

(defn- game-over? [board]
  (ttt/game-is-over? board
                     (board/available-spaces board)))


(defn analyze [board current-player opponent depth]
  ; (println (str "differential: " (- (ttt/advantage-count board current-player) (ttt/advantage-count board opponent))))
  (if (ttt/winner board)
    (if (= (ttt/winner board) current-player)
      (- 10 depth)
      (- depth 10))
    (let [player-advantage-count (ttt/advantage-count board current-player)
          opponent-advantage-count (ttt/advantage-count board opponent)]
      (cond
        (> player-advantage-count opponent-advantage-count) (- 5 depth)
        (> opponent-advantage-count player-advantage-count) (- depth 5)
        :else 0))))

(defn best-score [scores multiplier]
  (println (str "multiplier: " multiplier " scores: " scores))
    ; (if (> multiplier 0)
      (or (println (str "Post sort: " (sort #(< (%1 1) (%2 1)) scores)))
          (last (sort #(< (%1 1) (%2 1)) scores))))
      ; (last (sort #(> (%1 1) (%2 1)) scores))))

(defn- mm-algorithm [board current-player opponent-player location multiplier depth]
   (if (or (> depth 9) (game-over? board))
     (vector location (* multiplier (analyze board current-player opponent-player depth)))
     (let [available (board/available-spaces board)
           states (map (fn [spot]
                         (vector spot
                                (board/make-move board
                                                 spot
                                                 current-player)))
                       available)
           scores (reduce (fn [bb [location board]]
                          (conj bb (maxi board
                                         current-player
                                         opponent-player
                                         location
                                         multiplier
                                         depth)))
                        []
                        states)]
  (println "-----------------------")
  (cli/print-board board println)
       (let [score (best-score scores multiplier)
             tree (conj tree score)]
         (or (println (str "scores: " scores " depth: " depth))
             (println (str "score: " score))
         ; (println (str "depth: " depth))
         (println (str "TREE: " tree))
         score)))))
                       ; (absolute-best-score tree multiplier))))))

(defn maxi [board current-player opponent-player location multiplier depth]
  (mm-algorithm board opponent-player current-player location (* -1 multiplier) (+ 1 depth)))

(defn minimax [board current-player opponent-player]
  (first (mm-algorithm board current-player opponent-player nil -1 0)))
