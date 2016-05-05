(ns ttt-clojure.game
  (:require [ttt-clojure.board :as board]
            [ttt-clojure.cli :as cli]))

(defn create-new-game ([player1 player2]
                (create-new-game (board/new-board)
                                 player1
                                 player2))
  ([board player1 player2]
   {:board board
    :player1 player1
    :player2 player2}))

(defn- game-over? [game]
  (board/game-over? (:board game)))

(defn- game-has-winner? [game]
  (not (board/tie? (:board game))))

(defn- get-other-player [game current-player]
  (if (= (:player1 game) current-player)
    (:player2 game)
    (:player1 game)))

(defn get-next-move [game current-player]
  (cli/prompt-move
    (board/available-spaces (:board game))
    current-player))

(defn make-move [game move]
  (let [new-board (board/make-move
                    (:board game)
                    (:location move)
                    (:player move))
        mutated-game (conj game {:board new-board})]
    mutated-game))

(defn start
  ([] (start (create-new-game)))
  ([game] (start game (:player1 game)))
  ([game current-player]
  (loop [game game
         current-player current-player]
    (if (game-over? game)
      (if (game-has-winner? game)
        (cli/report-winner current-player)
        (cli/report-tie))
      (recur (make-move game (get-next-move game current-player))
                        (get-other-player game current-player))))))
