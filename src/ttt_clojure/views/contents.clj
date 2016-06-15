(ns ttt-clojure.views.contents
  [:require [hiccup.form :as form]
            [hiccup.element :as element]])

(defn index []
  [:div#welcome
   [:h1.header "Would you like to start a new game?"]
   (form/form-to [:post "/games"]
                 (form/submit-button {:class "btn btn-primary"}
                                     "Create game"))])

(defn not-found []
  [:div#not-found
   [:h1.header "This is not the page you are looking for..."]
   (element/link-to {:class "btn btn-primary"} "/" "Take me home")])

(def vertical-seperator
  [:div "------------------------------"])

(def horizontal-seperator
 "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")

(defn style-row [row]
  (let [styled-row (interpose horizontal-seperator row)]
    styled-row))

(defn add-indexes [board]
  (vec (map-indexed (fn [idx itm]
                      (if (not (= " " itm))
                        itm
                        idx))
                    board)))

(defn display-board [board]
  (let [board-with-indexes (add-indexes board)
        rows (partition 3 board-with-indexes)
        styled-rows (map #(style-row %) rows)
        styled-board (interpose vertical-seperator styled-rows)]
    styled-board))

(defn show ([id game available-moves]
   [:div#game
    [:h1.game-show (str "Game: " id)]
    [:p (display-board (:board game))]
    (form/form-to [:post (str "/games/" id)]
                  (form/drop-down "location" available-moves)
                  (form/hidden-field "player" "X")
                  (form/submit-button {:class "btn btn-primary"}
                                      "Choose move"))])
  ([id game _ outcome]
   [:div#game
    [:h1.game-show (str "Game: " id)]
    [:p (display-board (:board game))]
    (if outcome
      [:p (str "Game over " outcome " wins")]
      [:p (str "Game over cats game")])
    [:h3.header "Would you like to play again?"]
    (form/form-to [:post "/games"]
                  (form/submit-button {:class "btn btn-primary"}
                                      "Play Again"))]))
