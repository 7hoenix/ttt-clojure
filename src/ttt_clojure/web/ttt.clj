(ns ttt-clojure.web.ttt
  (:require [ttt-clojure.basic-game :as game]
            [ttt-clojure.ttt-rules :as ttt]
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

(defn- get-type [player]
  (if (= (type player) 'ttt-clojure.players.web.Web)
    :web
    :ai))

(defn- include-game-over [info]
  (assoc-in info [:game :game-over] (game/game-over? (:game info))))

(defn- include-outcome [info]
  (let [game-over (game/game-over? (:game info))]
    (if game-over
      (assoc-in info [:game :outcome] (ttt/outcome (get-in info [:game :board])))
      info)))

(defn- include-current-player-type [info]
  (let [current-player (get-in info [:game :current-player])]
    (assoc-in info [:game :type-of-player] (str (type current-player)))))

(defn- attach-game-info [game-info]
  (-> (include-game-over game-info)
      (include-outcome)
      (include-current-player-type)))

(defn show-game [repo]
  (fn [request]
    (let [id (get-id request)
          game (store/show-game repo id)]
      (http/json-response (attach-game-info game)))))

(defn update-game [repo]
  (fn [request]
    (let [id (get-id request)
          move (get-move request)
          mutated-game (store/make-move repo id move)]
      (http/json-response (attach-game-info mutated-game)))))

(defn create-game [repo]
  (fn [request]
    (http/json-response (attach-game-info (store/create-game repo)))))

(defn ai-move [repo]
  (fn [request]
    (let [id (get-id request)
          game (store/show-game repo id)
          move (player/take-turn (get-in game [:game :current-player])
                                 (get-in game [:game :board]))]
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
