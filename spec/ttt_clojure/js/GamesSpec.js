describe("Game", function() {
  describe("createGame", function() {
   	it("posts the game creation request to the server", function() {
      let body = JSON.stringify({ id: "1" , game: { board: [" ", " ", " "]}})
      let res = new window.Response(body, {
        status: 200,
        headers: {
          'Content-type': 'application/json'
        }
      });

      spyOn(window, "fetch").and.returnValue(Promise.resolve(res));

      createGame();
      expect(window.fetch).toHaveBeenCalledWith('/games', { method: 'POST'});
  	});
  });

  describe("displayGame", function() {
    it("sets the game id on the #game-id div", function() {
      let response = {id: 1, game: {board: [" ", " "]}};

      withFixture(function() {
        let el = addFixture('div', function(el) {
          el.id = 'game-id';
        });

        displayGame(response);
        expect(document.querySelector( "#game-id").innerText).toEqual('1');
      });
    });
  });

  describe("getId", function() {
    it("gets the game id from the #game-id div", function() {
      withFixture(function() {
        let el = addFixture('div', function(el) {
          el.id = 'game-id';
          el.textContent = '9';
        });

        let id = getId();
        expect(id).toEqual(9);
      });
    });
  });
});
