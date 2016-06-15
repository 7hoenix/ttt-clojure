(ns ttt-clojure.cli.game-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.cli.game :as game]
            [ttt-clojure.basic-game :as basic-game]
            [ttt-clojure.players.cli :as cli]
            [ttt-clojure.ttt-rules :as ttt]))

(describe "game loop"
  (around [it]
          (with-out-str (it)))

    (it "ends the game if it has a winner"
        (let [board ["X" " " " "
                     " " "X" " "
                     " " " " "X"]
              current-player (cli/create-cli "X" "O")
              opponent (cli/create-cli "O" "X")
              game (basic-game/create-new-game board
                                         current-player
                                         opponent)]
      (should=
        "X"
        (ttt/outcome (:board (game/run game))))))

    (it "ends the game if it has a tie"
        (let [board ["X" "O" "X"
                     "X" "X" "O"
                     "O" "X" "O"]
              current-player (cli/create-cli "X" "O")
              opponent (cli/create-cli "O" "X")
              game (basic-game/create-new-game board
                                         current-player
                                         opponent)]
      (should=
        false
        (ttt/outcome (:board (game/run game)))))))
