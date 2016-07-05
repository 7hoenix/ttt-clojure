function updateGame(id, location, player) {
	return fetch('/games/' + id, {
		method: 'PUT',
		headers:  {
			"Content-type": "application/x-www-form-urlencoded; charset=UTF-8"
		},
		body: 'location=' + location + '&player=' + player
	}).then(function(response) { return response.json();
	}).then(displayGame);
};

function createGame() {
	return fetch('/games', {
		method: 'POST'
	}).then(function(response) { return response.json();
	}).then(displayGame);
};

function displayBoard(id, board) {
	for (index in board) {
		let location = document.querySelector( `.location[data-board-idx="${index}"]`);
		if (!location) { continue; }
		let locationClone = location.cloneNode(true);
		location.parentNode.replaceChild(locationClone, location)

		locationClone.innerText = board[index]
	}
};

function getHumanMove(id, board, updateFunc) {
	for (index in board) {
		let location = document.querySelector( `.location[data-board-idx="${index}"]`);
		if (!location) { continue; }

		if (board[index] === " ") {
			location.addEventListener("click", function() {
				updateFunc(id, location.attributes.getNamedItem('data-board-idx').value, "X");
			});
		}
	}
};

function displayGame(response) {
	let idDiv = document.querySelector( "#game-id" )
	idDiv.innerText = response.id;
	displayBoard(response.id, response.game.board);
};

function getComputerMove(id, board, updateFunc) {
	return fetch('/ai-move/' + id, {
		method: 'GET'
	}).then(function(response) { return response.json();
	}).then(function(resp) { updateFunc(resp.id, resp.location, resp.player) });
};
//
// function getCurrentPlayersTurn(board, player1, player2) {
// 	let p1Count = 0
// 	let p2Count = 0;
// 	for (index in board) {
// 		if (board[index] === player1.symbol) {
// 			p1Count += 1;
// 		} else if (board[index] === player2.symbol) {
// 			p2Count += 1;
// 		} else {
// 			continue;
// 		}
// 	}
// 	if (p2Count >= p1Count) {
// 		return player1;
// 	} else if (p1Count > p2Count) {
// 		return player2;
// 	}
// };

function gameTick(id, board, currentPlayer, opponent, updater, moveGetters) {
	return fetch('/game-over/' + id, {
		method: 'GET'
	}).then(function(response) { return response.json();
	}).then(function(resp) {
		if (resp.gameOver === true) {
			displayBoard(id, board);
			break;
		} else {
			moveGetters[currentPlayer.type](id, board, updater);
		}
	});
};
