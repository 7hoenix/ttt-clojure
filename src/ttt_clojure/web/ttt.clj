(ns ttt-clojure.web.ttt
  (:require [ttt-clojure.basic-game :as game]
            [ttt-clojure.views.games  :as games]
            [ttt-clojure.util.http :as http]
            [ttt-clojure.player :as player]
            [ttt-clojure.game-storage :as store]))

(defn- str->int [s]
  (Integer. s))

(defn- get-id [req]
  (str->int (re-find #"\d+" (:uri req))))

(defn- get-move [request]
  {:location (str->int (get-in request [:params "location"]))
   :player (get-in request [:params "player"])})

(defn lookup-game [repo id]
  (get (store/games repo) id "Not found"))

(defn show-game [repo]
  (fn [request]
    (let [id (get-id request)
          game (lookup-game repo id)]
      (http/json-response game))))

(defn update-game [repo]
  (fn [request]
    (let [id (get-id request)
          game (lookup-game repo id)
          move (get-move request)]
      (http/json-response (store/make-move repo id move)))))

(defn create-game [repo]
  (fn [request]
    (http/json-response (store/create-game repo))))

(defn ai-move [repo]
  (fn [request]
    (let [id (get-id request)
          game (store/show-game repo id)
          move (player/take-turn (:player2 game) (:board game))]
    (http/json-response (merge move {:id id})))))

(defn home [repo]
  (fn [request]
    (http/basic-response (games/home))))

(def routes
  (let [repo (store/create-atom-game-repo)]
    {:post
      {"/games" (create-game repo)}
     :get
      {"/" (home repo)
       "/games/" (show-game repo)
       "/ai-move/" (ai-move repo)}
     :put
      {"/games/" (update-game repo)}
      :not-found (fn [req] (http/not-found "Not Found"))}))
