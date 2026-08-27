Customer Service
localhost:8081
       ▲
       │
       │ RestTemplate
       │
       │
Order Service
localhost:8082
       ▲
       │
     Postman

//

First
GET http://localhost:8081/api/customers/1

You need to get the customer.

Second
GET http://localhost:8082/api/orders/1

You need to get:

{
    "id": 1,
    "customerId": 1,
    "itemName": "Veg Pizza",
    "amount": 1299.0,
    "status": "Confirmed"
}
Finally 🚀
GET http://localhost:8082/api/orders/1/customer

Now Order Service calls Customer Service using RestTemplate.

Expected:

{
    "id": 1,
    "name": "Keerthi",
    "email": "keerthi@gmail.com",
    "phone": "9876543210"
}
