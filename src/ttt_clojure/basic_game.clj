(ns ttt-clojure.basic-game
  (:require [ttt-clojure.board :as board]
            [ttt-clojure.ttt-rules :as ttt]))

(defn game-over? [game]
  (let [board (:board game)]
    (ttt/game-is-over? game)))

(defn game-has-winner? [game]
  (not (ttt/is-tie? (board/available-spaces (:board game)))))

(defn create-new-game ([player1 player2] (create-new-game (board/new-board)
                                                          player1
                                                          player2))
  ([board player1 player2] {:board board
                            :player1 player1
                            :player2 player2}))

(defn make-move [game move]
  (let [new-board (board/make-move (:board game)
                                   (:location move)
                                   (:player move))
        mutated-game (conj game {:board new-board})]
    mutated-game))
