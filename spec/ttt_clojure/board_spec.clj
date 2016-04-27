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
    (let [the-board (board/make-move @empty-board 5 board/x-mark)]
      (should=
        '(0 1 2 3 4 6 7 8)
        (board/available-spaces the-board))))

  (it "checks if move is valid"
      (let [the-board (board/make-move @empty-board 5 board/x-mark)]
        (should=
          false
          (board/valid-move? the-board 5))))

  (it "checks if game is over"
      (let [move-locations [0 1 2]
            game-over-board (reduce #(board/make-move % %2 board/x-mark) @empty-board move-locations)]
        (should=
         true
         (board/game-over? game-over-board))))
)

; UI, start a game, create a board. Human v. Human.
; Testing UI (what comes in and how do I test it).

;  (it "checks if game is over"
;  (it "prompts a user to play a game"

; UI interface
;  (it "gets input from user"
;  (it "prints board"
;  (it "asks for next move"
