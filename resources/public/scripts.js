(function () {
	var httpRequest;
	var elements = document.getElementsByClassName("location");

	function takeTurn (value) {
		httpRequest = new XMLHttpRequest();
		httpRequest.onreadystatechange = alertContents;
		httpRequest.open('PUT', '/games/1');
		httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		httpRequest.send("location=" + encodeURIComponent(value) + "&player=X");
	}

	function alertContents () {
		if (httpRequest.readyState === XMLHttpRequest.DONE) {
			if (httpRequest.status === 200) {
				var response = JSON.parse(httpRequest.responseText);
				updateBoard(response);
			} else {
				console.log("not cool");
			}
		}
	};

	function updateBoard (response) {
		console.log(response);
		for (var i=0; i < response.length; i++) {
			let value = response[i];
			let element = elements[i];
			if (value === "X" || value === "O") {
				// element.innerText = value;
				let elClone = element.cloneNode(true);
				if (value === "X") {
					fetch('/images/x_icon.png')
						.then(function(response) {
							return response.blob();
						})
						.then(function(myBlob) {
							var objectURL = URL.createObjectURL(myBlob);
							elClone.src = objectURL;
						});
				} else if (value === "O") {
					fetch('/images/o_icon.png')
						.then(function(response) {
							return response.blob();
						})
						.then(function(myBlob) {
							var objectURL = URL.createObjectURL(myBlob);
							elClone.src = objectURL;
						});
				}
				element.parentNode.replaceChild(elClone, element);
			} else {
				// element.innerText = i;
			}
		}
	};

	for (var i=0; i < elements.length; i++) {
		let value = i;
		elements[i].addEventListener("click", function () {
			takeTurn(value.toString());
		});
	}

	function loadBoard () {
		httpRequest = new XMLHttpRequest();
		httpRequest.onreadystatechange = alertContents;
		httpRequest.open('GET', '/game-board/1');
		httpRequest.send(null);
	}

	loadBoard();
})();
