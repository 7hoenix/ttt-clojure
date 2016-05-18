(ns ttt-clojure.mini-max
  (:require [ttt-clojure.board :as board]
            [ttt-clojure.ttt-rules :as ttt]))

(declare minimax maxi mini)

(defn- game-over? [board]
  (ttt/game-is-over? board
                     (board/available-spaces board)))

(defn score [board multiplier]
  (if (ttt/winner board)
    (if (> 0 multiplier)
      10
      -10)
    0))

(defn which-player [player1 player2 multiplier]
  (if (> 0 multiplier)
    player2
    player1))

(defn best-score [tree multiplier]
  (println (str "best score: " tree))
  (println (str "max score " (apply max tree)))
  (println (str "min score: " (apply min tree)))
  (if (< 0 multiplier)
    (apply max tree)
    (apply min tree)))

(defn minimax ([board player1 player2] (minimax board maxi player1 player2 1))
  ([board func player1 player2 multiplier]
    (if (game-over? board)
      (or (println (str "score: " (score board multiplier) " multiplier: " multiplier))
          (score board multiplier))
      (let [available (board/available-spaces board)
            player (which-player player1
                                 player2
                                 multiplier)
            states (map #(board/make-move board
                                          %
                                          player)
                                          available)
            tree (reduce (fn [bb board]
                           (println (str "bb: " bb))
                   (conj bb
                         (func bb
                               board
                               player1
                               player2
                               multiplier)))
                []
                states)]
        (best-score tree multiplier)))))

(defn maxi [best-board board player1 player2 multiplier]
  (println (str "maxi: " multiplier))
  (minimax board mini player1 player2 (* -2 multiplier)))

(defn mini [best-board board player1 player2 multiplier]
  (println (str "mini: " multiplier))
  (minimax board maxi player1 player2 (* -3 multiplier)))
