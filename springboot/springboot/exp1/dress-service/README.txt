1. GET — Service Info
GET http://localhost:8080/api/info
2. GET — All dresses
GET http://localhost:8080/api/dresses
3. GET — One dress
GET http://localhost:8080/api/dresses/1
4. POST — Add
POST http://localhost:8080/api/dresses
{
    "id": 3,
    "name": "Denim Casual Dress",
    "category": "Casual",
    "price": 1599,
    "size": "M"
}
5. PUT — Update
PUT http://localhost:8080/api/dresses/1
{
    "id": 1,
    "name": "Premium Floral Summer Dress",
    "category": "Casual",
    "price": 1499,
    "size": "L"
}
6. DELETE
DELETE http://localhost:8080/api/dresses/3