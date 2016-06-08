(ns ttt-clojure.ttt-handlers
  (:require [ttt-clojure.game-storage :as store]
            [ttt-clojure.views.layout :as layout]
            [ttt-clojure.views.contents :as contents]
            [ring.util.response :as response]
            [clojure.string :as string]
            [hiccup.core :as hiccup]))

(def basic-response
  {:status 200
   :headers {"Content-Type" "text/html"}})

(def not-found
  {:status 404
   :headers {"Content-Type" "text/html"}
   :body (layout/application "Not Found" (contents/not-found))})

(defn- str->int [s]
  (Integer. s))

(defn- get-id [request]
  (str->int (re-find #"\d+" (:uri request))))

(defn- get-move [request]
  {:location (str->int (get-in request [:params "location"]))
   :player (get-in request [:params "player"])})

(defn new-game [request]
  (merge basic-response
         {:body (layout/application "Home"
                                    (contents/index))}))

(defn game [request]
  (let [id (get-id request)
        game (store/game id)
        available-moves (range 0 8)]
    (if (not (empty? game))
      (merge basic-response
             {:body (layout/application "show" (contents/show id
                                                              game
                                                              available-moves))})
      not-found)))

(defn create-game [request]
  (let [game (store/create-game)
        games (deref store/games)
        id (:current-index games)]
    (response/redirect (str "/games/" id))))

(defn update-game [request]
  (let [id (get-id request)
        move (get-move request)]
    (store/make-move id move)
    (response/redirect (str "/games/" id))))

(def handlers
  {:get
   {"/" new-game
    "/games/" game
    "/favicon.ico" (println "cake")}
   :post
   {"/games" create-game
    "/games/" update-game}})
