const test = require('node:test');
const assert = require('node:assert/strict');
const { app } = require('../server');

function startServer() {
  return new Promise((resolve) => {
    const server = app.listen(0, () => {
      resolve(server);
    });
  });
}

test('GET /api/items returns an array', async () => {
  const server = await startServer();
  try {
    const port = server.address().port;
    const response = await fetch(`http://127.0.0.1:${port}/api/items`);
    assert.equal(response.status, 200);
    const body = await response.json();
    assert.ok(Array.isArray(body));
  } finally {
    await new Promise((resolve) => server.close(resolve));
  }
});

test('POST /api/items creates a new item', async () => {
  const server = await startServer();
  try {
    const port = server.address().port;
    const response = await fetch(`http://127.0.0.1:${port}/api/items`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name: 'Notebook', description: 'Study notes' })
    });

    assert.equal(response.status, 201);
    const body = await response.json();
    assert.equal(body.name, 'Notebook');
    assert.equal(body.description, 'Study notes');
  } finally {
    await new Promise((resolve) => server.close(resolve));
  }
});

test('PUT and DELETE work for an item', async () => {
  const server = await startServer();
  try {
    const port = server.address().port;
    const createResponse = await fetch(`http://127.0.0.1:${port}/api/items`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name: 'Pen', description: 'Blue pen' })
    });
    const createdItem = await createResponse.json();

    const updateResponse = await fetch(`http://127.0.0.1:${port}/api/items/${createdItem.id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name: 'Pen', description: 'Black pen' })
    });
    assert.equal(updateResponse.status, 200);

    const deleteResponse = await fetch(`http://127.0.0.1:${port}/api/items/${createdItem.id}`, {
      method: 'DELETE'
    });
    assert.equal(deleteResponse.status, 204);
  } finally {
    await new Promise((resolve) => server.close(resolve));
  }
});
