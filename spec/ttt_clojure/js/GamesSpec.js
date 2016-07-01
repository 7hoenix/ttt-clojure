describe("Game", function() {
  let sandbox;

  beforeEach(function() {
    sandbox = withFixture(function() {
      let el = addFixture('div', function(el) {
        el.id = 'game-id';
        el.textContent = '9';
      });

      for (index in [0, 1, 2, 3, 4, 5, 6, 7, 8]) {
        addFixture('div', function(el) {
          el.className = 'location';
          addAttribute('data-board-idx', index, function(attr) {
            el.attributes.setNamedItem(attr);
          });
        });
      }
    });
  });

  afterEach(function() {
    removeFixture(sandbox);
  });


  describe("createGame", function() {
   	it("posts the game creation request to the server", function(done) {
      let body = JSON.stringify({ id: "1" , game: { board: [" ", " ", " "]}})
      let res = new window.Response(body, {
        status: 200,
        headers: {
          'Content-type': 'application/json'
        }
      });

      spyOn(window, "fetch").and.returnValue(Promise.resolve(res));

      createGame().then(function() {
        expect(window.fetch).toHaveBeenCalledWith('/games', { method: 'POST'});
      }).then(done);
  	});
  });

  describe("displayGame", function() {
    it("sets the game id on the #game-id div", function() {
      let response = {id: 1, game: {board: [" ", " "]}};

      displayGame(response)

      expect(document.querySelector( "#game-id").innerText).toEqual('1');
    });
  });

  describe("updateGame", function() {
    it("hands a put request to update the game board to the server", function(done) {
      let body = JSON.stringify({ id: "3" , game: { board: [" ", " ", "X"]}})
      let res = new window.Response(body, {
        status: 200,
        headers: {
          'Content-type': 'application/json'
        }
      });

      spyOn(window, "fetch").and.returnValue(Promise.resolve(res));

      updateGame(3, 2, "X").then(function() {
        expect(window.fetch).toHaveBeenCalledWith('/games/3', {
          method: 'PUT',
          headers: {
            "Content-type": "application/x-www-form-urlencoded; charset=UTF-8"
          },
          body: "location=2&player=X"
        });
      }).then(done);
    });
  });

  describe("displayBoard", function() {
    it("attaches click event listeners to each of the open locations on the board", function() {
      let board = [" ", "X", " ", " ", "O", " ", " ", "X", " "];

      let updater = jasmine.createSpy('updateGame')

      displayBoard(3, board, updater);

      for (idx in board) {
    		let el = document.querySelector( `.location[data-board-idx="${idx}"]`);
        el.click();
      }

      expect(updater.calls.count()).toEqual(6);
    });
  });

  describe("getComputerMove", function() {
    it("sends a get request to the ai move route", function(done) {
      let body = JSON.stringify({ id: "3", location: "2", player: "O"})
      let res = new window.Response(body, {
        status: 200,
        headers: {
          'Content-type': 'application/json'
        }
      });

      spyOn(window, "fetch").and.returnValue(Promise.resolve(res));
      let updater = jasmine.createSpy('updateGame')

      getComputerMove(3, updater).then(function() {
        expect(window.fetch).toHaveBeenCalledWith('/ai-move/3', { method: 'GET', });
        expect(updater).toHaveBeenCalledWith("3", "2", "O")
      }).then(done);
    });
  });

  describe("getCurrentPlayersTurn", function() {
    it("dynamically calculates the next players turn based on the board", function() {
      let board = [" ", "X", " ", " ", "O", " ", " ", "X", " "];
      let player1 = {symbol: "X"}
      let player2 = {symbol: "O"}

      expect(getCurrentPlayersTurn(board, player1, player2)).toEqual(player2);
    })

    it("Defaults to player1", function() {
      let board = [" ", " ", " ", " ", " ", " ", " ", " ", " "];
      let player1 = {symbol: "X"}
      let player2 = {symbol: "O"}

      expect(getCurrentPlayersTurn(board, player1, player2)).toEqual(player1);
    });
  });
});
