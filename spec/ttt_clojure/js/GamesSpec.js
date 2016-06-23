describe("isValidMove", function() {
	let game;

	it("returns true if the spot is not occupied.", function() {
		let board = [" ", " ", " ", " ", " ", " ", " ", " ", " "];
		let player = "X";
		let move = 0;

		expect(isValidMove(board, player, move)).toEqual(true);
	});
});
