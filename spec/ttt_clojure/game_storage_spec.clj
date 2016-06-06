(ns ttt-clojure.game-storage-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.game-storage :as store]))

(defn reset-games []
  (store/clear-db))

(describe "Game Storage"
          (it "can calculate the next id"
              (reset-games)
              (store/next-id)
              (should=
                2
                (store/next-id)))

          (it "can create a new game"
              (reset-games)
              (should=
                [" " " " " " " " " " " " " " " " " "]
                (:board
                    (store/create-game))))

          (it "can make a move"
              (reset-games)
              (store/create-game)
              (let [move {:location 0 :player "X"}]
                (should=
                  ["X" " " " " " " " " " " " " " " " "]
                  (:board
                      (store/make-move 1 move)))))

          (it "can return the requested game"
              (reset-games)
              (store/create-game)
              (should=
                [" " " " " " " " " " " " " " " " " "]
                (:board
                  (store/game 1)))))
