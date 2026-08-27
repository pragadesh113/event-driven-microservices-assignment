package com.example.notificationservice.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.notificationservice.event.OrderCreatedEvent;

@Component
public class OrderCreatedNotificationConsumer {

    @RabbitListener(queues = "notification.queue")
    public void handleOrderCreated(OrderCreatedEvent event) {

        System.out.println("===== Notification Service =====");
        System.out.println("Order Created Notification");
        System.out.println("Order ID: " + event.getOrderId());
        System.out.println("Product: " + event.getProduct());
        System.out.println("Quantity: " + event.getQuantity());
        System.out.println("================================");
    }
}