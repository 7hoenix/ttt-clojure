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
            (store/show-game @test-repo 1)))))
