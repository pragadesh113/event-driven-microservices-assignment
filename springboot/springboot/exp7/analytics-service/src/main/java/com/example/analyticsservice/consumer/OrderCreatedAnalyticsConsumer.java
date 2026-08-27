package com.example.analyticsservice.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.analyticsservice.event.OrderCreatedEvent;

@Component
public class OrderCreatedAnalyticsConsumer {

    @RabbitListener(queues = "analytics.queue")
    public void handleOrderCreated(OrderCreatedEvent event) {

        System.out.println("===== Analytics Service =====");
        System.out.println("Order Created Event Received");
        System.out.println("Order ID: " + event.getOrderId());
        System.out.println("Product: " + event.getProduct());
        System.out.println("Quantity: " + event.getQuantity());

        // Analytics processing can be performed here
        System.out.println("Analytics updated for product: "
                + event.getProduct());

        System.out.println("=============================");
    }
}