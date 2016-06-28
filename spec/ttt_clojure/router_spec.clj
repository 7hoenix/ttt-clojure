(ns ttt-clojure.router-spec
  (:require [speclj.core :refer :all]
            [speclj.stub]
            [ring.mock.request :as mock]
            [ttt-clojure.router :as router]))

(describe "Basic Routes"
  (with-stubs)

  (it "invokes the given function as the path"
    (let [home-page (stub :home-page)
          routes (router/make-routes {:get
                                      {"/" home-page}})
          request (mock/request :get "/")]
      (routes request)
      (should-have-invoked :home-page {:times 1})))

  (it "responds with a 404 error if the path is not found"
    (let [bad-page (stub :bad-page)
          routes (router/make-routes {:not-found bad-page})
          request (mock/request :get "/cakeeeeee")]
      (routes request)
      (should-have-invoked :bad-page {:times 1}))))
