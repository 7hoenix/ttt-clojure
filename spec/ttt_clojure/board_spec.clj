(ns ttt-clojure.board-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.board :as board]))

(defn- make-moves [b mark locations]
  (reduce #(board/make-move % %2 mark) b locations))

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
      (should=
        '(0 1 2 3 4 6 7 8)
        (-> @empty-board
            (board/make-move 5 board/x-mark)
            (board/available-spaces)))))
