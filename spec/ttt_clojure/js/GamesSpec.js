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
      var el = addFixture('div', function(el) {
        el.id = 'game-id';
      });

      displayGame(response);
      expect(document.querySelector( "#game-id").innerText).toEqual('1');
    });
  });
});

function withFixture(fn) {
  let sandboxDiv = document.createElement('div');
  sandboxDiv.id = 'sandbox';
  document.body.appendChild(sandboxDiv);

  fn();

  // remove sandbox div
}

function addFixture(elementType, builder) {
  var el = document.createElement(elementType);
  builder(el);

  document.querySelector('#sandbox').appendChild(el)
  return el;
}

/*
Setup HTML docuemnt fixture.

JSdom.

document with body

mock out fetch.

Create body/elements that game expects.

let result = new Promise

jasmine-jquery => set fixtures

*/
