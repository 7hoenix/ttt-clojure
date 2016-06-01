(ns ttt-clojure.game-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.game :as game]
            [ttt-clojure.players.cli :as cli]
            [ttt-clojure.ttt-rules :as ttt]
            [ttt-clojure.board :as board]))

(defn- check-winner [board]
  (ttt/winner board))

(describe "game loop"
  (around [it]
          (with-out-str (it)))

    (it "ends the game if it has a winner"
        (let [board ["X" " " " "
                     " " "X" " "
                     " " " " "X"]
              current-player (cli/create-cli "X" "O")
              opponent (cli/create-cli "O" "X")
              game (game/create-new-game board
                                         current-player
                                         opponent)]
      (should=
        "X"
        (check-winner (game/start game)))))

    (it "ends the game if it has a tie"
        (let [board ["X" "O" "X"
                     "X" "X" "O"
                     "O" "X" "O"]
              current-player (cli/create-cli "X" "O")
              opponent (cli/create-cli "O" "X")
              game (game/create-new-game board
                                         current-player
                                         opponent)]
      (should=
        false
        (check-winner (game/start game)))))

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
              game (game/create-new-game board
                                         current-player
                                         opponent)]
          (should=
            (conj game {:board new-board})
            (game/make-move game move)))))
