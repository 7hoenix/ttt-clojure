(ns ttt-clojure.cli-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.cli :as cli]))

(describe "command line interface or cli"
  (around [it]
          (with-out-str (it)))

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
                      (cli/prompt-move [1 2 3]))))

  (it "reports winner to user"
      (should=
        "X wins"
        (cli/report-winner "X")))

  (it "reports tie"
      (should=
        "Cats game"
        (cli/report-tie "don't care"))))
