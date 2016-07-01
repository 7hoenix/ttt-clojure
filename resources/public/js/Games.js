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

function displayBoard(id, board, updateFunc) {
	for (index in board) {
		let location = document.querySelector( `.location[data-board-idx="${index}"]`);
		if (!location) { continue; }

		if (board[index] === " ") {
			location.addEventListener("click", function() {
				updateFunc(id, location.attributes.getNamedItem('data-board-idx').value, "X");
			});
		}

		location.innerText = board[index]
	}
};

function displayGame(response) {
	let idDiv = document.querySelector( "#game-id" )
	idDiv.innerText = response.id;
	displayBoard(response.id, response.game.board, updateGame);
};

function getComputerMove(id, updateFunc) {
	return fetch('/ai-move/' + id, {
		method: 'GET'
	}).then(function(response) { return response.json();
	}).then(function(resp) { updateFunc(resp.id, resp.location, resp.player) });
};

function getCurrentPlayersTurn(board, player1, player2) {
	let p1Count = 0
	let p2Count = 0;
	for (index in board) {
		if (board[index] === player1.symbol) {
			p1Count += 1;
		} else if (board[index] === player2.symbol) {
			p2Count += 1;
		} else {
			continue;
		}
	}
	if (p2Count >= p1Count) {
		return player1;
	} else if (p1Count > p2Count) {
		return player2;
	}
};
