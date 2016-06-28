function Game (id) {
	this.updateBoard = function (board, move, player) {
		let payload = {
			board: board,
			move: move,
			player: player
		};
		var data = new FormData();
		data.append( "json", JSON.stringify( payload ) );

		fetch('/games/' + id, {
			method: 'put',
			body: data
		}).then(function(response) { return response.json();
		}).then(function(data) { alert( JSON.stringify( data ) ) });

	}
}

Game.prototype.makeMove = function (board, move, player) {
	if (isValidMove(board, move)) {
		this.updateBoard(board, move, player);
	}
};

function isValidMove(board, move) {
	return (board[move] === " " && move < board.length) ? true : false;
};
