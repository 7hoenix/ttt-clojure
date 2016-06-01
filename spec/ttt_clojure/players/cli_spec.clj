(ns ttt-clojure.players.cli-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.players.cli :as cli]))

(describe "Command Line Interface"
  (around [it]
          (with-out-str (it)))

  (with printer println)

  (it "prompts a user to start a new game"
      (should=
        true
        (cli/new-game @printer (fn [] "y"))))

  (it "prompts a user for a move"
      (should=
        0
        (:location (cli/turn-cycle [" " " " " " " " " " " " " " " "]
                                   "X"
                                   "O"
                                   @printer
                                   (fn [] 0)))))

  (it "prints the board"
      (should=
        "X | 1 | O\n---------\n3 | 4 | 5\n---------\n6 | 7 | 8"
        (cli/print-board ["X" " " "O" " " " " " " " " " " " "] @printer))))
