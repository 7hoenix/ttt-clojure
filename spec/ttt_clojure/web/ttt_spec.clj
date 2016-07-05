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
          (:game game-info)
          (:game
            (:body ((ttt/show-game repo) request)))))))

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

; (describe "ai-move"
;   (it "sends a request to minimax and returns the move"
;     (let [repo (store/create-atom-game-repo)
;           board [" " "O" " " " " " " " " " " " "  " "]
;           request (mock/request :get "/ai-move/1")]
;       (swap! (store/games repo assoc 1 {:board }))
;       (store/make-move repo 1 {:location 0
;                                :player "X"})
;         (should=
;           {:location 4
;            :player "O"
;            :id 1}
;           (:body ((ttt/ai-move repo) request))))))
