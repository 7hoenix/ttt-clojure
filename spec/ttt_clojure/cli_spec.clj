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
  )


; UI, start a game, create a board. Human v. Human.
; Testing UI (what comes in and how do I test it).

; UI interface

;  (it "prints board"
;  (it "asks for next move"
