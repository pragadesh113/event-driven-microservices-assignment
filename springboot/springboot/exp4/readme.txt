# API Gateway

## Objective

Implement a simple API Gateway to route requests to Customer Service and Order Service.

## Architecture

```text
                 API Gateway
                    :8080
                      |
          +-----------+-----------+
          |                       |
          v                       v
 Customer Service            Order Service
     :8081                      :8082


Then test Gateway

First test Customer:

GET http://localhost:8080/api/customers/1

If that works, test Order:

GET http://localhost:8080/api/orders/1

And finally, because you already implemented Experiment 3:

GET http://localhost:8080/api/orders/1/customer

That last one demonstrates both experiments together:

Postman
   ↓
API Gateway :8080
   ↓
Order Service :8082
   ↓
RestTemplate
   ↓
Customer Service :8081
