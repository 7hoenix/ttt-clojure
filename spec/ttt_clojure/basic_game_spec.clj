(ns ttt-clojure.basic-game-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.basic-game :as basic-game]
            [ttt-clojure.players.cli :as cli]))

(describe "Basic Game"
  (it "makes a move on the board"
      (let [board [" " "O" "X"
                   "X" "X" "O"
                   " " "X" "O"]
            new-board [" " "O" "X"
                       "X" "X" "O"
                       "O" "X" "O"]
            move {:location 6 :player "O"}
            current-player (cli/create-cli "X" "O")
            opponent (cli/create-cli "O" "X")
            game (basic-game/create-new-game board
                                       current-player
                                       opponent)]
        (should=
          (conj game {:board new-board})
          (basic-game/make-move game move)))))
