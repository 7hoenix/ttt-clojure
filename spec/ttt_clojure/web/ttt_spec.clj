(ns ttt-clojure.web.ttt-spec
  (:require [speclj.core :refer :all]
            [ring.middleware.params :as params]
            [ring.mock.request :as mock]
            [ttt-clojure.game-storage :as store]
            [ttt-clojure.web.ttt :as ttt]))

(describe "lookup-game"
  (it "returns game info for a game"
    (let [game {:board ["X"]}
          game-repo {1 game}]
      (should=
        ["X"]
        (:board (ttt/lookup-game game-repo 1)))))

  (it "returns a string of not found if not found"
    (let [game-repo {}]
      (should=
        "Not found"
        (ttt/lookup-game game-repo 1)))))

(describe "Show game"
  (it "renders the proper game page"
    (let [game {:board ["X" " "]}
          game-repo {1 game}
          request (mock/request :get "/games/1")]
      (should=
        ["X" " "]
        (:board ((ttt/show-game game-repo) request))))))

(describe "Update game"
  (it "returns the game with an updated board"
    (let [game {:board ["X" " "]}
          game-repo {1 game}
          request (mock/request :put "/games/1" {:location "1"
                                                 :player "O"})]
      (should=
        ["X" "O"]
        (:board ((params/wrap-params (ttt/update-game game-repo)) request))))))

(describe "Create game"
  (it "returns the newly created game"
    (let [game-repo (store/create-atom-game-repo)
          request (mock/request :post "/games")]
      (should=
        1
        (:id ((ttt/create-game game-repo) request))))))
