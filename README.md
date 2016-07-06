# Tic Tac Toe in Clojure

## Installation/playing instructions

Clone down the repo.

Then you have two different options in terms of how you'd like to play it.

### Option 1 => Command Line Interface

![Command Line version demo](http://g.recordit.co/7PdbfFtoZX.gif)

`$ lein run`

### Option 2 => Web Interface

![Web version demo](http://g.recordit.co/c6181v5IFn.gif)

`$ lein with-profile web run`

## Key Features

* Two different targets to choose from (web or cli).
* Play against an unbeatable AI using the negamax (minimax) algorithm... it get's old really quickly!
* Back end core logic and CLI version is built in Clojure.
* Web version is served directly on top of Ring (with NO Compojure).
* Front end is built with JS.
* Pretty colors in the new front end thanks to [Adobes cool color wheel](color.adobe.com).
