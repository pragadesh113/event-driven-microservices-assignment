package com.example.paymentservice.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.paymentservice.event.OrderCreatedEvent;

@Component
public class OrderCreatedConsumer {

    @RabbitListener(queues = "order.queue")
    public void handleOrderCreated(OrderCreatedEvent event) {

        System.out.println("===== OrderCreated Event Received =====");
        System.out.println("Order ID: " + event.getOrderId());
        System.out.println("Product: " + event.getProduct());
        System.out.println("Quantity: " + event.getQuantity());
        System.out.println("=======================================");
    }
}