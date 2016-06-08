(ns ttt-clojure.server-old
  (:require [ttt-clojure.game :as game]
            [ttt-clojure.game-storage :as store]
            [hiccup.core :as hiccup]
            [ttt-clojure.views.layout :as layout]
            [ttt-clojure.views.contents :as contents]
            [ring.util.response :as response]
            [ring.mock.request :as mock]
            [ring.middleware.params :as params]
            [ring.middleware.reload :as reloader]))

;; generalize what server is doing into high level and low level concepts.
;; high level => business concepts like TTT.
;; low level => handling responses and requests.

(defn- str->int [s]
  (Integer. s))

(defn- get-id [uri]
  (str->int (re-find #"\d+" uri)))

(defn- get-move [more]
  (let [params (first more)
        move {:location (str->int (get params "location"))
              :player (get params "player")}]
    move))

(defn show [uri]
  (let [id (get-id uri)
        game (store/game id)
        available-moves (store/available-moves id)]
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (layout/application "show" (contents/show id
                                                     game
                                                     available-moves))}))

(defn new-game []
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (layout/application "Home" (contents/index))})

(defn respond-to-get [uri & more]
  (condp = (re-find #"\D+" uri)
    "/games/" (show uri)
    "/" (new-game)
    "/favicon.ico" (println "cake")))

(defn respond-to-put [uri & more]
  (println uri)
  (println more)
  (let [id (get-id uri)
        updated-game (store/make-move
                       id
                       (get-move more))]
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (layout/application "show"
                               (contents/show id updated-game))}))

(defn respond-to-post [uri & more]
  (if (= (get (first more) "_method") "PUT")
    (respond-to-put uri more)
    (let [game (store/create-game)
          games (deref store/games)
          id (:current-index games)]
      (response/redirect (str "/games/" id)))))

(defn handler [request]
  (println request)
  ;; NEW stuff here.
  (condp = (:request-method request)
    :get (respond-to-get (:uri request))
    :post (respond-to-post (:uri request) (:params request))
    :put (respond-to-put (:uri request) (:params request))))

(def app
  (-> handler
      (reloader/wrap-reload)
      (params/wrap-params)))
