(ns ttt-clojure.cli-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.cli :as cli]))

(describe "command line interface or cli"
  (it "gets input from user"
      (should=
        "y"
        (with-redefs [println (fn [_] )]
          (with-in-str "Y     \n" (cli/get-input "OK")))))

  (it "prompts a user to start a new game"
      (should=
        true
        (with-redefs [cli/get-input (fn [_] "y")]
          (cli/new-game))))

  (it "prompts a user for a move"
      (should=
        false
        (with-redefs [cli/get-input (fn [_] "3")]
                      (cli/prompt-move [1 2 3]))))

  (it "re-prompts a user if input isn't valid"
      (should=
        2
        (with-redefs [cli/get-input (fn [_] "2")]
                      (cli/prompt-move [1 2 3])))))


; UI, start a game. Human v. Human.
