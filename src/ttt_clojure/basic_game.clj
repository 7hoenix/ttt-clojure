(ns ttt-clojure.basic-game
  (:require [ttt-clojure.board :as board]
            [ttt-clojure.ttt-rules :as ttt]))

(defn game-over? [game]
  (let [board (:board game)]
    (ttt/game-is-over? board)))

(defn game-has-winner? [board]
  (not (ttt/outcome board)))

(defn create-new-game ([player1 player2] (create-new-game (board/new-board)
                                                          player1
                                                          player2))
  ([board player1 player2] {:board board
                            :current-player player1
                            :other-player player2}))

(defn make-move [game move]
  (let [new-board (board/make-move (:board game)
                                   (:location move)
                                   (:player move))
        mutated-game (conj game {:board new-board})]
    mutated-game))
