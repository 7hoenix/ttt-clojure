(ns ttt-clojure.server
  (:require [ttt-clojure.game :as game]
            [ttt-clojure.game-storage :as store]
            [ring.adapter.jetty :as jetty]
            [ring.mock.request :as mock]
            [ring.middleware.params :as params]
            [ring.middleware.reload :as reloader]))

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
        game (store/game id)]
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (str "<html>" (:board game) "</html>")}))

(defn new-game []
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (str "<html><h1>Would you like to start a new game?</h1></html>")})

(defn respond-to-get [uri & more]
  (condp = (re-find #"\D+" uri)
    "/games/" (show uri)
    "/" (new-game)
    "/favicon.ico" (println "cake")))

(defn respond-to-put [uri & more]
  (let [updated-game (store/make-move
                       (get-id uri)
                       (get-move more))]
    {:status 200
     :headers {"Content-Type" "text/plain"}
     :body (str "<html>" (:board updated-game) "</html>")}))

(defn respond-to-post [uri & more]
  (let [game (store/create-game)]
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (str "<html>" (:board game)  "</html>")}))

(defn handler [request]
  (condp = (:request-method request)
    :get (respond-to-get (:uri request))
    :post (respond-to-post (:uri request))
    :put (respond-to-put (:uri request) (:params request))))

(def app
  (-> handler
      (reloader/wrap-reload)
      (params/wrap-params)))

(defonce server (jetty/run-jetty #'app {:port 8383 :join? false}))
