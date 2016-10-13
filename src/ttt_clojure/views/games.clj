(ns ttt-clojure.views.games
  (:require [hiccup.page :as page]
            [hiccup.element :as element]
            [hiccup.form :as form]))

(defn wrap-html [title & contents]
  (page/html5 {:ng-app "clojureTTT" :lang "en"}
              [:head
               [:title title]
               (page/include-css "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css")
               (page/include-css "/css/styles.css")]
               [:body
                [:div {:class "container"} contents]
                (page/include-js "/js/Games.js")
                (element/javascript-tag "window.onload = createGame")]))

(defn home []
  (wrap-html (str "Home")
             [:div#game
               [:h1#header "Tic Tac Toe, game: "]
               [:h3#game-id]
               [:div#board.centered
                 [:div.row
                   [:div.location {:data-board-idx 0}]
                   [:div.location {:data-board-idx 1}]
                   [:div.location {:data-board-idx 2}]]
                 [:div.row
                   [:div.location {:data-board-idx 3}]
                   [:div.location {:data-board-idx 4}]
                   [:div.location {:data-board-idx 5}]]
                 [:div.row
                   [:div.location {:data-board-idx 6}]
                   [:div.location {:data-board-idx 7}]
                   [:div.location {:data-board-idx 8}]]]]))
