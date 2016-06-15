(ns ttt-clojure.web.game-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.web.game :as game]
            [ttt-clojure.players.web :as web]
            [ttt-clojure.players.ai :as ai]
            [ttt-clojure.basic-game :as basic-game]))

(describe "Web Game"
  (it "it takes a game state and returns it with a computer move"
    (with-stubs)

    (let [board ["X" "O" " "
                 "X" "X" " "
                 "O" "X" "O"]
          current-player (web/create-web "X" "O")
          opponent (ai/create-ai "O" "X")
          game (basic-game/create-new-game board
                                           current-player
                                           opponent)]
      (should=
        {:location 5 :player "O"}
        (game/get-move game)))))
