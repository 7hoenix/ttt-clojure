(ns ttt-clojure.views.games
  (:require [hiccup.page :as page]
            [hiccup.util :as util]))

(defn show [id game available-moves]
  (page/html5 {:ng-app "clojureTTT" :lang "en"}
              [:head
               [:title (str "games/" id)]
               (page/include-css "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css")
               (page/include-css "/styles.css")]
              [:body
               [:h1#header (str "Tic Tac Toe, Game: " id)]
               [:div#board.centered
                [:div.row
                 [:div
                  [:img.location]]
                 [:div
                  [:img.location]]
                 [:div
                  [:img.location]]]
                [:div.row
                 [:div
                  [:img.location]]
                 [:div
                  [:img.location]]
                 [:div
                  [:img.location]]]
                [:div.row
                 [:div
                  [:img.location]]
                 [:div
                  [:img.location]]
                 [:div
                  [:img.location]]]]
               (page/include-js "/scripts.js")]))
