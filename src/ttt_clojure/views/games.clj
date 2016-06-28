(ns ttt-clojure.views.games
  (:require [hiccup.page :as page]
            [hiccup.form :as form]))

(defn wrap-html [title & contents]
  (page/html5 {:ng-app "clojureTTT" :lang "en"}
              [:head
               [:title title]
               (page/include-css "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css")
               (page/include-css "/css/styles.css")]
               [:body
                [:div {:class "container"} contents]
                (page/include-js "/js/Games.js")]))


(defn home []
  (wrap-html (str "Home")
    [:div#welcome
     [:h1.header "Would you like to start a new game?"]
     (form/form-to [:post "/games"]
                   (form/submit-button {:class "btn btn-primary"}
                                       "Create game"))]))

(defn show [game-id]
   (wrap-html (str "game " + game-id)
             [:h1#header (str "Tic Tac Toe, Game: " game-id)]
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
                [:img.location]]]]))
