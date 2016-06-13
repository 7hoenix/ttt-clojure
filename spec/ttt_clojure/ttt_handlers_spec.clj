(ns ttt-clojure.ttt-handlers-spec
  [:require [speclj.core :refer :all]
            [speclj.stub]
            [ring.mock.request :as mock]
            [ring.middleware.params :as params]
            [ttt-clojure.ttt-handlers :as handlers]
            [ttt-clojure.game-storage :as store]])

(defn wrap-it [handler]
  (params/wrap-params handler))

(extend-type clojure.lang.APersistentMap
  store/GameRepository
  (games [this] ((:games-index this)))

  (create-game [this] ((:create-game this)))

  (show-game [this id] ((:show-game this) id))

  (make-move [this id move] ((:make-move this) id move)))

(describe "New game"
  (with-stubs)

  (it "renders the new game page"
    (let [new-game (stub :new-game)
          game-funcs {:new-game new-game}
          request (mock/request :get "/")
          handler (handlers/make-new-game game-funcs)]
      (should=
        200
        (:status (handler request))))))

(describe "Create game"
  (with-stubs)

  (let [games-index (stub :games-index {:return {:current-index 1}})
        create-game (stub :create-game {:return
                                        {:id 1
                                         :game {:board []}}})
        game-funcs {:games-index games-index
                    :create-game create-game}
        request (mock/request :post "/games")
        handler (handlers/make-create-game game-funcs)]

    (it "sends a command to create a new game"
        (handler request)
        (should-have-invoked :create-game {:times 1}))

    (it "renders the show page for the newly created game"
        (should=
          "/games/1"
          (get-in (handler request)
                  [:headers "Location"])))))

(describe "Show game"
  (with-stubs)

    (it "sends a command to get a specific game"
      (let [show-game (stub :show-game)
            game-funcs {:show-game show-game}
            request (mock/request :get "/games/1")
            handler (handlers/make-show-game game-funcs)]
        (handler request)
        (should-have-invoked :show-game {:with [1] :times 1})))

    (it "renders the 404 page if game is not found"
      (let [show-game (stub :show-game {:return {}})
            game-funcs {:show-game show-game}
            request (mock/request :get "/games/1")
            handler (handlers/make-show-game game-funcs)]
        (should=
          404
          (:status (handler request)))))

    (it "rders the appropriate game show page if found"
      (let [show-game (stub :show-game
                            {:return {:board [" " " " " "]}})
            game-funcs {:show-game show-game}
            request (mock/request :get "/games/1")
            handler (handlers/make-show-game game-funcs)]
        (should=
          200
          (:status (handler request))))))

(describe "Update game"
  (with-stubs)

  (it "sends the command to update a game with the move"
      (let [make-move (stub :make-move)
            show-game (stub :show-game
                            {:return {:board []}})
            game-funcs {:make-move make-move
                        :show-game show-game}
            request (mock/request :post "/games/1" {:location "0"
                                                    :player "X"})
            handler (handlers/make-update-game game-funcs)]
        ((wrap-it handler) request)
        (should-have-invoked :make-move
                             {:with [1 {:location 0
                                        :player "X"}]
                              :times 1})))

  (it "renders the appropriate game show page after updating"
      (let [make-move (stub :make-move)
            show-game (stub :show-game
                            {:return {:board []}})
            game-funcs {:make-move make-move
                        :show-game show-game}
            request (mock/request :post "/games/1" {:location "0"
                                                    :player "X"})
            handler (handlers/make-update-game game-funcs)]
        (should=
          "/games/1"
          (get-in ((wrap-it handler) request)
                  [:headers "Location"])))))
