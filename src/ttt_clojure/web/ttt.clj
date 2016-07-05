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

(defn- attach-analysis [game-info]
  (let [game-over (game/game-over? (:game game-info))]
    (if-let [outcome game-over]
      (merge game-info {:game-over game-over
                        :outcome outcome})
      game-info)))

(defn show-game [repo]
  (fn [request]
    (let [id (get-id request)
          game (store/show-game repo id)]
      (http/json-response (attach-analysis game)))))

(defn update-game [repo]
  (fn [request]
    (let [id (get-id request)
          move (get-move request)
          mutated-game (store/make-move repo id move)]
      (http/json-response (attach-analysis mutated-game)))))

(defn create-game [repo]
  (fn [request]
    (http/json-response (attach-analysis (store/create-game repo)))))

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
