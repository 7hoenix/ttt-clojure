(ns ttt-clojure.game-storage-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.game-storage :as store]))

(describe "Game Storage"
  (with test-repo (store/create-atom-game-repo))

  (it "can create a new game"
      (should=
        [" " " " " " " " " " " " " " " " " "]
        (get-in (store/create-game @test-repo)
                [:game :board])))

  (it "can make a move"
      (store/create-game @test-repo)
      (let [move {:location 0 :player "X"}]
        (should=
          ["X" " " " " " " " " " " " " " " " "]
          (:board
            (:game
                (store/make-move @test-repo 1 move))))))

  (it "can return the requested game"
      (store/create-game @test-repo)
      (should=
        [" " " " " " " " " " " " " " " " " "]
        (:board
          (:game
            (store/show-game @test-repo 1)))))

  (it "can return the updated game"
      (store/create-game @test-repo)
      (let [game-info (store/show-game @test-repo 1)
            updated-board ["X" "X" "O" "O" " " " " " " " " " "]
            updated-game (assoc-in game-info [:game :board] updated-board)]
      (should=
        ["X" "X" "O" "O" " " " " " " " " " "]
        (:board
          (:game
            (store/update-game @test-repo 1 updated-game)))))))
