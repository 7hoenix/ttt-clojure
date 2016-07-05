(ns ttt-clojure.cli.game
  (:require [ttt-clojure.board :as board]
            [ttt-clojure.basic-game :as basic-game]
            [ttt-clojure.player :as player]
            [ttt-clojure.ttt-rules :as ttt]))

(defn run ([game] (run game (:current-player game) (:other-player game)))
  ([game current-player opponent]
   (if (ttt/game-is-over? (:board game))
     (or (if (ttt/outcome (:board game))
           (player/display current-player (str (ttt/outcome (:board game)) " wins"))
           (player/display current-player (str "Cats game")))
         game)
     (let [mutated-game (basic-game/make-move game
                                   (player/take-turn current-player
                                                     (:board game)))]
       (recur mutated-game opponent current-player)))))
