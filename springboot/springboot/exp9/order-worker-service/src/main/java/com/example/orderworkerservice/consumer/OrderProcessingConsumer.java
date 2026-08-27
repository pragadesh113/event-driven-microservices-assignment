package com.example.orderworkerservice.consumer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.orderworkerservice.config.RabbitMQConfig;
import com.example.orderworkerservice.event.OrderCreatedEvent;

@Component
public class OrderProcessingConsumer {

    private final Map<String, Integer> attempts = new ConcurrentHashMap<>();

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void processOrder(OrderCreatedEvent event) {
        int attempt = attempts.merge(event.getOrderId(), 1, Integer::sum);
        System.out.println("Processing " + event.getOrderId() + ", attempt " + attempt);

        if ("FAIL".equalsIgnoreCase(event.getProduct())) {
            throw new IllegalStateException("Demo failure requested");
        }

        attempts.remove(event.getOrderId());
        System.out.println("Order processed successfully: " + event.getProduct());
    }

    @RabbitListener(queues = RabbitMQConfig.DLQ)
    public void inspectDeadLetter(OrderCreatedEvent event) {
        System.out.println("DLQ received failed order: " + event.getOrderId());
        attempts.remove(event.getOrderId());
    }
}
