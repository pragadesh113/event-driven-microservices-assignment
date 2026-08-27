const express = require('express');

const app = express();
const port = process.env.PORT || 3000;

app.use(express.json());

let items = [
  { id: 1, name: 'Book', description: 'Learning material' },
  { id: 2, name: 'Laptop', description: 'Work device' }
];

app.get('/health', (req, res) => {
  res.json({ status: 'ok' });
});

app.get('/api/items', (req, res) => {
  res.json(items);
});

app.get('/api/items/:id', (req, res) => {
  const item = items.find((entry) => entry.id === Number(req.params.id));
  if (!item) {
    return res.status(404).json({ message: 'Item not found' });
  }
  res.json(item);
});

app.post('/api/items', (req, res) => {
  const { name, description } = req.body;
  if (!name) {
    return res.status(400).json({ message: 'Name is required' });
  }

  const newItem = {
    id: Date.now(),
    name,
    description: description || ''
  };

  items.push(newItem);
  res.status(201).json(newItem);
});

app.put('/api/items/:id', (req, res) => {
  const item = items.find((entry) => entry.id === Number(req.params.id));
  if (!item) {
    return res.status(404).json({ message: 'Item not found' });
  }

  const { name, description } = req.body;
  if (name !== undefined) item.name = name;
  if (description !== undefined) item.description = description;

  res.json(item);
});

app.delete('/api/items/:id', (req, res) => {
  const index = items.findIndex((entry) => entry.id === Number(req.params.id));
  if (index === -1) {
    return res.status(404).json({ message: 'Item not found' });
  }

  items.splice(index, 1);
  res.status(204).send();
});

if (require.main === module) {
  app.listen(port, () => {
    console.log(`Server running at http://localhost:${port}`);
  });
}

module.exports = { app };
