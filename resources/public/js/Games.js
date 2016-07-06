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

function getComputerMove(id, board, updateFunc) {
	return fetch('/ai-move/' + id, {
		method: 'GET'
	}).then(function(response) { return response.json();
	}).then(function(resp) { updateFunc(resp.id, resp.location, resp.player) });
};

function last3Characters(str) {
	return str.substr(str.length - 3)
}

function displayOutcome(outcome) {
	alert(outcome ? outcome : "cat's game")
}

function displayGame(response) {
	let idDiv = document.querySelector( "#game-id" )
	idDiv.innerText = response.id;
	displayBoard(response.id, response.game.board);
	let playerType = last3Characters(response.game["type-of-player"]);

	if (response.game["game-over"] === true) {
		displayOutcome(response.game["outcome"]);
	} else if (playerType === "Web") {
		getHumanMove(response.id, response.game["board"], updateGame)
	} else if (playerType === ".AI") {
		getComputerMove(response.id, response.game["board"], updateGame)
	}
};
