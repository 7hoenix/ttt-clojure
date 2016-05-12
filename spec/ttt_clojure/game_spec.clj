(ns ttt-clojure.game-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.game :as game]
            [ttt-clojure.ui :as ui]
            [ttt-clojure.board :as board]))

(describe "game loop"
  (with-stubs)
  (around [it]
          (with-out-str (it)))


    (it "ends the game if it has a winner"
        (let [board ["X" " " " "
                     " " "X" " "
                     " " " " "X"]
              current-player "X"
              game (game/create-new-game board current-player "O")]
      (should=
        "O wins"
        (game/start game current-player (fn [_] "O wins") read-line))))

    (it "ends the game if it has a tie"
        (let [board ["X" "O" "X"
                     "X" "X" "O"
                     "O" "X" "O"]
              current-player "X"
              game (game/create-new-game board current-player "O")]
      (should=
        "Cats game"
        (game/start game current-player (fn [_] "Cats game") read-line))))

    (it "gets the next move for the player that isn't the current player"
          (let [board [" " "O" "X"
                       "X" "X" "O"
                       " " "X" "O"]
                current-player "X"
                game (game/create-new-game board current-player "O")]
            (should=
              {:location 0 :player "X"}
              (game/get-next-move game
                                  current-player
                                  println
                                  (fn [] 0)))))

    (it "makes a move on the board"
        (let [board [" " "O" "X"
                     "X" "X" "O"
                     " " "X" "O"]
              new-board [" " "O" "X"
                         "X" "X" "O"
                         "O" "X" "O"]
              move {:location 6 :player "O"}
              game (game/create-new-game board "X" "O")]
          (should=
            (conj game {:board new-board})
            (game/make-move game move)))))
