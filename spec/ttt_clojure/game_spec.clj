(ns ttt-clojure.game-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.game :as game]
            [ttt-clojure.board :as board]))

(describe "game"
    (with one-back-board
      ["X" "X" " " " " " " " " " " " " " " " "])

    (with two-back-board
      ["X" " " " " " " " " " " " " " " " " " "])

  (it "loop works for last move"
      (let [board @one-back-board
            move {:location 2 :symbol "X"}]
      (should=
        true
        (game/game-loop board (fn [_] move)))))

  (it "loop works for last 2 moves"
      (let [board @two-back-board
            moves [{:location 1 :symbol "X"}
                   {:location 2 :symbol "X"}]]
        ; (should=
        ;   true
        ;   (with-redefs [cli/prompt-move (fn [_] (1 moves))]
        ;     (game/game-loop board)
      ; )

  )))
