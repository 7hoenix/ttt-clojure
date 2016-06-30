function withFixture(fn) {
  let sandboxDiv = document.createElement('div');
  sandboxDiv.id = 'sandbox';
  document.body.appendChild(sandboxDiv);

  fn()

  removeFixture(sandboxDiv);
}

function addFixture(elementType, builder) {
  var el = document.createElement(elementType);
  builder(el);

  document.querySelector('#sandbox').appendChild(el)
  return el;
}

function removeFixture(el) {
  document.body.removeChild(el);
}
