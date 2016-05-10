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
            (board/available-spaces))))

  (it "checks if move is valid"
      (should=
        false
        (-> @empty-board
            (board/make-move 5 board/x-mark)
            (board/valid-move? 5))))

  (it "checks if game is over"
      (should=
        true
        (-> @empty-board
            (make-moves board/x-mark [0 1 2])
            (board/game-over?))))

  (it "checks if a game is over with a different winning sequence"
      (should=
        true
        (-> @empty-board
            (make-moves board/x-mark [0 4 8])
            (board/game-over?))))

  (it "makes sure that the sequence has to be the same symbol"
      (should=
        false
        (-> @empty-board
            (make-moves board/x-mark [0 8])
            (board/make-move 4 board/x-mark)
            (board/game-over?)
            (not))))

  (it "knows all winning sequences"
      (should=
        board/winning-seqs
        [[0 1 2] [3 4 5] [6 7 8]
         [0 3 6] [1 4 7] [2 5 8]
         [0 4 8] [2 4 6]]))

  (it "allows either player to win the game"
      (should=
        true
        (-> @empty-board
            (make-moves board/o-mark [0 4 8])
            (board/game-over?))))

  (it "handles a cats game"
      (should=
        true
        (-> @empty-board
            (make-moves board/x-mark [0 1 5 6 8])
            (make-moves board/o-mark [2 3 4 7])
            (board/tie?))))
)
