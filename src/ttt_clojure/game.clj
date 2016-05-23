(ns ttt-clojure.game
  (:require [ttt-clojure.board :as board]
            [ttt-clojure.player :as player]
            [ttt-clojure.ttt-rules :as ttt]))

(defn create-new-game ([player1 player2] (create-new-game (board/new-board)
                                                          player1
                                                          player2))
  ([board player1 player2] {:board board
                            :player1 player1
                            :player2 player2}))

(defn- game-over? [game]
  (let [board (:board game)]
    (ttt/game-is-over? board
                       (board/available-spaces board))))

(defn- game-has-winner? [game]
  (not (ttt/is-tie? (board/available-spaces (:board game)))))

(defn make-move [game move]
  (let [new-board (board/make-move (:board game)
                                   (:location move)
                                   (:player move))
        mutated-game (conj game {:board new-board})]
    mutated-game))

(defn start ([game] (start game (:player1 game) (:player2 game) println read-line))
  ([game current-player opponent print-func prompt-func]
   (if (game-over? game)
     (or (if (game-has-winner? game)
           (player/display current-player (str opponent " wins"))
           (player/display current-player (str "Cats game")))
         (:board game))
     (let [mutated-game (make-move game
                                   (player/take-turn current-player
                                                     (:board game)))]
       (recur mutated-game opponent current-player print-func prompt-func)))))
