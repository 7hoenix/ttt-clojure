(ns ttt-clojure.game
  (:require [ttt-clojure.board :as board]
            [ttt-clojure.ui :as ui]
            [ttt-clojure.ttt-rules :as ttt]))

(defn create-new-game ([] (create-new-game "X" "O"))
  ([player1 player2]
   (create-new-game
     (board/new-board)
     player1
     player2))
  ([board player1 player2]
   {:board board
    :player1 player1
    :player2 player2}))

(defn- game-over? [game]
  (let [board (:board game)]
    (ttt/game-is-over? board
                       (board/available-spaces board))))

(defn- game-has-winner? [game]
    (not (ttt/is-tie? (board/available-spaces (:board game)))))

(defn get-other-player [game current-player]
  (if (= (:player1 game) current-player)
    (:player2 game)
    (:player1 game)))

(defn get-next-move [board current-player opponent-player disp-func prompt-func]
  (prompt-func board
               current-player
               opponent-player
               disp-func))

(defn make-move [game move]
  (let [new-board (board/make-move (:board game)
                                   (:location move)
                                   (:player move))
        mutated-game (conj game {:board new-board})]
    mutated-game))

(defn start
  ([] (start (create-new-game)))
  ([game] (start game (:player1 game) (:player2 game) println read-line))
  ([game current-player opponent-player disp-func prompt-func]
   (ui/print-board disp-func (:board game))
   (if (game-over? game)
     (if (game-has-winner? game)
       (and (ui/report-winner disp-func
                         opponent-player)
            (:board game))
       (and (ui/report-tie disp-func "tie")
            (:board game)))
     (let [mutated-game (make-move game
                                   (get-next-move (:board game)
                                                  current-player
                                                  opponent-player
                                                  disp-func
                                                  prompt-func))]
       (recur mutated-game opponent-player current-player disp-func prompt-func)))))
