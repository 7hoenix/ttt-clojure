(defproject ttt-clojure "0.1.0-SNAPSHOT"
  :description "Tic Tac Toe in Clojure"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring/ring-mock "0.3.0"]]
  :profiles {:dev {:dependencies [[speclj "3.3.1"]]}}
  :plugins [[speclj "3.3.1"]
            [ring "1.5.0-RC1"]]
  :test-paths ["spec"]
  :main ttt-clojure.core)
