describe("isValidMove", function() {
	let game;

	it("returns true if the spot is not occupied.", function() {
		let board = [" ", " ", " ", " ", " ", " ", " ", " ", " "];
		let move = 0;

		expect(isValidMove(board, move)).toEqual(true);
	});
});
