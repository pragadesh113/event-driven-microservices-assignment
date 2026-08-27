# Spring Boot Microservices Demo

Two independent Spring Boot applications demonstrating service-information endpoints and service-to-service communication.

## Services

| Service          | Port | Endpoints |
|------------------|------|-----------|
| customer-service | 8081 | GET /customer/service-info |
| order-service    | 8082 | GET /order/service-info, GET /order/customer-info-resttemplate, GET /order/customer-info-webclient |

## How to run

Open two terminals.

Terminal 1 (Customer Service):

    cd customer-service
    mvn spring-boot:run

Terminal 2 (Order Service):

    cd order-service
    mvn spring-boot:run

## Service-to-service communication

The order-service calls customer-service's `/customer/service-info` endpoint using both **RestTemplate** and **WebClient**, exposed via:

- `GET /order/customer-info-resttemplate`
- `GET /order/customer-info-webclient`

## Demonstrate from Postman

Use the **Spring Boot Microservices Demo** collection. Start customer-service first, then order-service, then send the requests. The customer-info requests return the customer service's response nested inside, proving cross-service communication.

Requires Java 17+ and Maven.
