function updateGame(id, location, player) {
	return fetch('/games/' + id, {
		method: 'PUT',
		headers:  {
			"Content-type": "application/x-www-form-urlencoded; charset=UTF-8"
		},
		body: 'location=' + location + '&player=' + player
	}).then(function(response) { return response.json();
	}).then(displayGame);
}

function getId() {
	return parseInt(document.querySelector( "#game-id" ).innerText);
};

function createGame() {
	return fetch('/games', {
		method: 'POST'
	}).then(function(response) { return response.json();
	}).then(displayGame);
};

function displayBoard(board, updateFunc) {
	for (index in board) {
		let location = document.querySelector( `.location[data-board-idx="${index}"]`);
		if (!location) { continue; }

		if (board[index] === " ") {
			location.addEventListener("click", function() {
				updateFunc(getId(), location.attributes.getNamedItem('data-board-idx').value, "X");
			});
		}

		location.innerText = board[index]
	}
};

function displayGame(response) {
	let idDiv = document.querySelector( "#game-id" )
	idDiv.innerText = response.id;
	displayBoard(response.game.board, updateGame);
};
