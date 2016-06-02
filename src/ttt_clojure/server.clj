(ns ttt-clojure.server
  (:require [ttt-clojure.game :as game]
            [ttt-clojure.players.ai :as ai]
            [ttt-clojure.players.web :as web]
            [ring.mock.request :as mock]
            [ring.middleware.params :as params]))

(def create-players
  {:player1 (web/create-web "X" "O")
   :player2 (ai/create-ai "O" "X")})

(def games
  (atom {}))

(defn next-id []
  (let [index (or (:current-index @games) 0)
        id (inc index)]
    (swap! games assoc
           :current-index
           id)
    id))

(defn create-game []
  (let [players create-players
        game (swap! games assoc
                    (next-id)
                    (game/create-new-game
                      (:player1 players)
                      (:player2 players)))]
    game))


(defn str->int [s]
  (Integer. s))

(defn- get-id [uri]
   (str->int (re-find #"\d+" uri)))

(defn show [uri]
  (let [id (get-id uri)
        game (get @games id)]
  {:body {id game}}))

(defn make-move [uri more]
  (let [id (get-id uri)
        game (get @games id)
        move {:location (str->int (get (first more) "spot"))
              :player (get (first more) "symbol")}
        updated-game (game/make-move game move)]
    (swap! games assoc
           id
           updated-game)))

(defn new-game []
  {:body "Would you like to start a new game?"})

(defn respond-to-get [uri & more]
  (condp = (re-find #"\D+" uri)
    "/games/" (show uri)
    "/" (new-game)))

(defn respond-to-put [uri & more]
  {:body (make-move uri more)})

(defn respond-to-post [uri & more]
  {:body (create-game)})

(defn handler [request]
  ; (println request)
  (condp = (:request-method request)
    :get (respond-to-get (:uri request))
    :post (respond-to-post (:uri request))
    :put (respond-to-put (:uri request) (:params request))))

(def app
  (-> handler
      (params/wrap-params)))
