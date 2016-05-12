(ns ttt-clojure.ui-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.ui :as ui]))

(describe "user interface"
  (around [it]
          (with-out-str (it)))

  (with printer println)

  (it "reports the winner"
      (should=
        "X wins"
        (ui/report-winner @printer "X")))

  (it "prompts a user to start a new game"
      (should=
        true
        (ui/new-game @printer (fn [] "y"))))

  (it "prompts a user for a move"
      (should=
        0
        (:location (ui/prompt-move @printer
                         (fn [] 0)
                         [0 1 2] "X"))))

  (it "reports winner to user"
      (should=
        "X wins"
        (ui/report-winner @printer "X")))

  (it "reports tie"
      (should=
        "Cats game"
        (ui/report-tie @printer "X")))

  (it "prints the board"
      (should=
        "X | 1 | O\n---------\n3 | 4 | 5\n---------\n6 | 7 | 8"
        (ui/print-board @printer
                         ["X" " " "O" " " " " " " " " " " " "]))))
