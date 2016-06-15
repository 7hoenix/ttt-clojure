(ns ttt-clojure.cli.game
  (:require [ttt-clojure.board :as board]
            [ttt-clojure.basic-game :as basic-game]
            [ttt-clojure.player :as player]
            [ttt-clojure.ttt-rules :as ttt]))

(defn game-has-winner? [game]
  (not (ttt/is-tie? game)))

(defn run ([game] (run game (:player1 game) (:player2 game) println read-line))
  ([game current-player opponent print-func prompt-func]
   (if (ttt/game-is-over? game)
     (or (if (ttt/outcome game)
           (player/display current-player (str (ttt/outcome " wins")))
           (player/display current-player (str "Cats game")))
         game)
     (let [mutated-game (basic-game/make-move game
                                   (player/take-turn current-player
                                                     (:board game)))]
       (recur mutated-game opponent current-player print-func prompt-func)))))
