(ns ttt-clojure.views.layout
  (:require [hiccup.page :as page]))

(defn application [title & content]
  (page/html5 {:ng-app "clojureTTT" :lang "en"}
              [:head
               [:title title]
               (page/include-css "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css")
               [:body
                [:div {:class "container"} content]]]))
