# Online Food Delivery - Microservices

## Experiment

Develop Customer Service and Order Service as two independent Spring Boot applications.

## Technologies

- Java
- Spring Boot
- Spring Web
- Maven
- REST API
- Postman
- ArrayList
- No Database

---

## 1. Customer Service

**Port:** `8081`

### Endpoints

```text
GET     /api/info
GET     /api/customers
GET     /api/customers/{id}
POST    /api/customers
PUT     /api/customers/{id}
DELETE  /api/customers/{id}
Run
mvnw.cmd spring-boot:run
Test
GET http://localhost:8081/api/info
GET http://localhost:8081/api/customers
GET http://localhost:8081/api/customers/1
POST
POST http://localhost:8081/api/customers
{
    "id": 3,
    "name": "Rahul",
    "email": "rahul@gmail.com",
    "phone": "9876012345"
}
PUT
PUT http://localhost:8081/api/customers/1
{
    "id": 1,
    "name": "Keerthi Kumar",
    "email": "keerthi@gmail.com",
    "phone": "9999999999"
}
DELETE
DELETE http://localhost:8081/api/customers/3
2. Order Service

Port: 8082

Endpoints
GET     /api/info
GET     /api/orders
GET     /api/orders/{id}
POST    /api/orders
PUT     /api/orders/{id}
DELETE  /api/orders/{id}
Run
mvnw.cmd spring-boot:run
Test
GET http://localhost:8082/api/info
GET http://localhost:8082/api/orders
GET http://localhost:8082/api/orders/1
POST
POST http://localhost:8082/api/orders
{
    "id": 3,
    "customerId": 1,
    "itemName": "Veg Pizza",
    "amount": 1599,
    "status": "Confirmed"
}
PUT
PUT http://localhost:8082/api/orders/1
{
    "id": 1,
    "customerId": 1,
    "itemName": "Paneer Biryani",
    "amount": 1499,
    "status": "Shipped"
}
DELETE
DELETE http://localhost:8082/api/orders/3
Architecture
Online Food Delivery Platform
        |
   +----+----+
   |         |
Customer   Order
Service    Service
  8081       8082
   |           |
ArrayList   ArrayList
Result

Customer Service and Order Service were successfully developed as two independent Spring Boot applications and tested using Postman.
