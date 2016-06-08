(ns ttt-clojure.ttt-handlers-spec
  [:require [speclj.core :refer :all]
            [speclj.stub]
            [ring.mock.request :as mock]
            [ring.middleware.params :as params]
            [ttt-clojure.ttt-handlers :as handlers]])

(defn wrap-it [handler]
  (params/wrap-params handler))

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

  (let [games (stub :games {:return {:current-index 1}})
        create-game (stub :create-game)
        game-funcs {:games games
                    :create-game create-game}
        request (mock/request :post "/games")
        handler (handlers/make-create-game game-funcs)]

    (it "sends a command to create a new game"
        (handler request)
        (should-have-invoked :create-game {:times 1}))

    (it "renders the show page for the newly created game"
        (should=
          "/games/1"
          (get
            (:headers
              (handler request))
            "Location")))))

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

    (it "renders the appropriate game show page if found"
      (let [show-game (stub :show-game
                            {:return {:not "empty"}})
            game-funcs {:show-game show-game}
            request (mock/request :get "/games/1")
            handler (handlers/make-show-game game-funcs)]
        (should=
          200
          (:status (handler request))))))

(describe "Update game"
  (with-stubs)

  (it "sends the command to update a game with the move"
      (let [update-game (stub :update-game)
            game-funcs {:update-game update-game}
            request (mock/request :post "/games/1" {:location "0"
                                                    :player "X"})
            handler (handlers/make-update-game game-funcs)]
        ((wrap-it handler) request)
        (should-have-invoked :update-game
                             {:with [1 {:location 0
                                        :player "X"}]
                              :times 1})))

  (it "renders the appropriate game show page after updating"
      (let [update-game (stub :update-game)
            game-funcs {:update-game update-game}
            request (mock/request :post "/games/1" {:location "0"
                                                    :player "X"})
            handler (handlers/make-update-game game-funcs)]
        (should=
          "/games/1"
          (get
            (:headers
              ((wrap-it handler) request))
            "Location")))))
