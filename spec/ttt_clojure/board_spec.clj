(ns ttt-clojure.board-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.board :as board]))

(describe "board"
  (with empty-board (board/new-board))

  (it "creates a board with 9 available spaces"
      (should= 9 (count @empty-board))
      (should=
        (map (fn [x] board/available-mark) (range 9))
        @empty-board))

  (it "sets the given mark on the board"
    (let [updated-board (board/make-move @empty-board 5 board/x-mark)]
      (should= board/x-mark (nth updated-board 5))))

  (it "gets available spaces on the board"
    (let [the-board (board/make-move (board/new-board) 5 board/x-mark)]
      (should=
        '(0 1 2 3 4 6 7 8)
        (board/available-spaces the-board))))

)

; UI, start a game, create a board. Human v. Human.
; Testing UI (what comes in and how do I test it).
