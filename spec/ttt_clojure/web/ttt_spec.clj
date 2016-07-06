(ns ttt-clojure.web.ttt-spec
  (:require [speclj.core :refer :all]
            [ring.middleware.params :as params]
            [ring.mock.request :as mock]
            [ttt-clojure.game-storage :as store]
            [ttt-clojure.web.ttt :as ttt]))

(describe "Show game"
  (it "returns the proper game as json based on a get request"
    (let [repo (store/create-atom-game-repo)
          game-info (store/create-game repo)
          request (mock/request :get "/games/1")]
      (should=
        [" " " " " " " " " " " " " " " " " "]
        (:board
          (:game
            (:body ((ttt/show-game repo) request))))))))

(describe "Update game"
  (it "returns the game with an updated board"
    (let [repo (store/create-atom-game-repo)
          game-info (store/create-game repo)
          request (mock/request :put "/games/1" {:location "1"
                                                 :player "O"})]
      (should=
        [" " "O" " " " " " " " " " " " " " "]
        (:board
          (:game
            (:body
              ((params/wrap-params (ttt/update-game repo)) request))))))))

(describe "Create game"
  (it "returns the newly created game"
    (let [repo (store/create-atom-game-repo)
          request (mock/request :post "/games")]
      (should=
        1
        (:id
          (:body ((ttt/create-game repo) request)))))))

(describe "ai-move"
  (it "sends a request to minimax and returns the move"
    (let [repo (store/create-atom-game-repo)
          game-info (store/create-game repo)
          board ["X" "O" "X" "O" "O" "X" "O" "X"  " "]
          current-player (get-in game-info [:game :current-player])
          other-player (get-in game-info [:game :other-player])
          updated-game (assoc-in game-info [:game :board] board)
          updated-game-c (assoc-in updated-game [:game :current-player] other-player)
          updated-game-o (assoc-in updated-game-c [:game :other-player] current-player)
          request (mock/request :get "/ai-move/1")]
      (store/update-game repo 1 updated-game-o)
      (should=
        {:location 8
         :player "O"
         :id 1}
        (:body ((ttt/ai-move repo) request))))))
