(ns ttt-clojure.cli-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.cli :as cli]))

(describe "command line interface or cli"
  (around [it]
          (with-out-str (it)))

  (with printer println)

  (it "gets input from user"
      (should=
        "y"
        (with-in-str "Y     \n" (cli/prompt
                                  @printer
                                  (fn [] "y")
                                  "Give me input"))))

  (it "prompts a user to start a new game"
      (should=
        true
        (cli/new-game @printer (fn [] "yes"))))

  (it "prompts a user for a move"
      (should=
        0
        (:location (cli/prompt-move @printer
                         (fn [] 0)
                         [0 1 2] "X"))))

  (it "reports winner to user"
      (should=
        "X wins"
        (cli/report-winner @printer "X")))

  (it "reports tie"
      (should=
        "Cats game"
        (cli/report-tie @printer "X")))

  (it "prints the board"
      (should=
        "X | 1 | O\n---------\n3 | 4 | 5\n---------\n6 | 7 | 8"
        (cli/print-board @printer
                         ["X" " " "O" " " " " " " " " " " " "]))))
