(ns ttt-clojure.ttt-handlers
  (:require [ring.util.response :as response]
            [ttt-clojure.views.layout :as layout]
            [ttt-clojure.views.contents :as contents]
            [ttt-clojure.game-storage :as store]
            [ttt-clojure.board :as board]))

(defn- str->int [s]
  (Integer. s))

(defn- get-id [req]
  (str->int (re-find #"\d+" (:uri req))))

(defn- get-move [request]
  (println (get-in request [:params "location"]))
  (println (get-in request [:params "player"]))
  {:location (str->int (get-in request [:params "location"]))
   :player (get-in request [:params "player"])})

(def basic-response
  {:status 200
   :headers {"Content-Type" "text/html"}})

(def not-found
  {:status 404
   :headers {"Content-Type" "text/html"}
   :body (layout/application "Not Found" (contents/not-found))})

(defn make-new-game [funcs]
  (fn [request]
    (merge basic-response
           {:body (layout/application "home"
                                      (contents/index))})))

(defn make-create-game [funcs]
  (fn [request]
    ((:create-game funcs))
    (let [games (deref (:games funcs))
          id (:current-index games)]
      (response/redirect (str "/games/" id)))))

(defn make-show-game [funcs]
  (fn [request]
    (let [id (get-id request)
          game ((:show-game funcs) id)
          available-moves (board/available-spaces (:board game))]
      (if (not (empty? game))
        (merge basic-response
               {:body (layout/application "show"
                                          (contents/show id
                                                         game
                                                         available-moves))})
        not-found))))

(defn make-update-game [funcs]
  (fn [request]
    (let [id (get-id request)
          move (get-move request)]
      ((:update-game funcs) id move)
      (response/redirect (str "/games/" id)))))

(def game-funcs
  {:games store/games
   :create-game store/create-game
   :show-game store/game
   :update-game store/make-move})

(def handlers
  {:get
   {"/" (make-new-game game-funcs)
    "/games/" (make-show-game game-funcs)
    "/favicon.ico" (println "cake")}
   :post
   {"/games" (make-create-game game-funcs)
    "/games/" (make-update-game game-funcs)}})
