function withFixture(fn) {
  let sandboxDiv = document.createElement('div');
  sandboxDiv.id = 'sandbox';

  document.body.appendChild(sandboxDiv);

  fn()

  return sandboxDiv
}

function addFixture(elementType, builder) {
  let el = document.createElement(elementType);
  builder(el);

  document.querySelector('#sandbox').appendChild(el)
  return el;
}

function addAttribute(attrType, attrValue, builder) {
  let attr = document.createAttribute(attrType);
  attr.value = attrValue;
  builder(attr);

  return attr;
}

function removeFixture(el) {
  document.body.removeChild(el);
}
