describe("isValidMove", function() {
	it("returns true if the spot is not occupied.", function() {
		let board = [" ", " ", " ", " ", " ", " ", " ", " ", " "];
		let move = 0;

		expect(isValidMove(board, move)).toEqual(true);
	});

	it("returns false if the spot is occupied", function() {
		let board = ["X", " ", " ", " ", " ", " ", " ", " ", " "];
		let move = 0;

		expect(isValidMove(board, move)).toEqual(false);
	});

	it("returns false if move is not on board", function() {
		let board = ["X", " ", " ", " ", " ", " ", " ", " ", " "];
		let move = 11;

		expect(isValidMove(board, move)).toEqual(false);
	});
});

describe("makeMove", function() {
	it("makes the move on the board", function() {
		let game = new Game(1);
		spyOn(game, "updateBoard");
		let player = "X";
		let move = 0;
		let board = [" ", " ", " ", " ", " ", " ", " ", " ", " "];
		game.makeMove(board, move, player);

		expect(game.updateBoard).toHaveBeenCalledWith(board, move, player);
	});
});
