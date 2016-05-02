(ns ttt-clojure.cli-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.cli :as cli]))

(describe "command line interface or cli"
  (it "gets input from user"
      (should=
        "cake"
        (with-in-str "cake" (cli/get-input "pie or cake?"))))


  (it "prompts a user to start a new game"
      (should=
        true
        (with-in-str "y" (cli/new-game))))

  (it "can handle bad data correctly"
      (should=
        true
        (with-in-str "Y \n" (cli/new-game))))

  (it "forces a user to select again if data is wrong"
      (should=
        true
        (with-in-str "lkasdfj\nY" (cli/new-game))))

  (it "prompts a user for a move"
      (should=
        true
        (with-in-str "2" (cli/prompt-move [1 2 3]))))

  (it "re-prompts a user if input isn't valid"
      (should=
        true
        (with-in-str "asdf\n5\n2" (cli/prompt-move [1 2 3]))))

  )


; UI, start a game. Human v. Human.
