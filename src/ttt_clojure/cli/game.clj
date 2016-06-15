(ns ttt-clojure.cli.game
  (:require [ttt-clojure.board :as board]
            [ttt-clojure.basic-game :as basic-game]
            [ttt-clojure.player :as player]
            [ttt-clojure.ttt-rules :as ttt]))

(defn game-over? [game]
  (let [board (:board game)]
    (ttt/game-is-over? board
                       (board/available-spaces board))))

(defn game-has-winner? [game]
  (not (ttt/is-tie? (board/available-spaces (:board game)))))

(defn run ([game] (run game (:player1 game) (:player2 game) println read-line))
  ([game current-player opponent print-func prompt-func]
   (if (basic-game/game-over? game)
     (or (if (basic-game/game-has-winner? game)
           (player/display current-player (str opponent " wins"))
           (player/display current-player (str "Cats game")))
         (:board game))
     (let [mutated-game (basic-game/make-move game
                                   (player/take-turn current-player
                                                     (:board game)))]
       (recur mutated-game opponent current-player print-func prompt-func)))))
