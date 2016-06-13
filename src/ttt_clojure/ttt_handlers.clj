(ns ttt-clojure.ttt-handlers
  (:require [ttt-clojure.util.http :as http]
            [ttt-clojure.views.layout :as layout]
            [ttt-clojure.views.contents :as contents]
            [ttt-clojure.game-storage :as store]
            [ttt-clojure.board :as board]))

(defn- str->int [s]
  (Integer. s))

(defn- get-id [req]
  (str->int (re-find #"\d+" (:uri req))))

(defn- get-move [request]
  {:location (str->int (get-in request [:params "location"]))
   :player (get-in request [:params "player"])})

(defn- render-game [id game]
  (let [available-moves (board/available-spaces (:board game))]
    (layout/application "Show"
                        (contents/show id
                                       game
                                       available-moves))))

(defn make-new-game [repo]
  (fn [request]
    (http/basic-response (layout/application "home"
                                             (contents/index)))))

(defn make-create-game [repo]
  (fn [request]
    (let [{:keys [id game]} (store/create-game repo)]
      (http/redirect (render-game id game) id))))

(defn make-show-game [repo]
  (fn [request]
    (let [id (get-id request)
          game (store/show-game repo id)
          available-moves (board/available-spaces (:board game))]
      (if-not (empty? game)
        (http/basic-response (render-game id game))
        (http/not-found (layout/application "Not Found" (contents/not-found)))))))

(defn make-update-game [repo]
  (fn [request]
    (let [id (get-id request)
          game (store/show-game repo id)
          move (get-move request)]
      (store/make-move repo id move)
      (http/redirect (render-game id game) id))))

(def handlers
  (let [game-repo (store/create-atom-game-repo)]
    {:get
     {"/" (make-new-game game-repo)
      "/games/" (make-show-game game-repo)
      "/favicon.ico" (println "cake")}
     :post
     {"/games" (make-create-game game-repo)
      "/games/" (make-update-game game-repo)}}))
