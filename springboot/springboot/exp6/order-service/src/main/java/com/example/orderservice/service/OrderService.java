package com.example.orderservice.service;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.example.orderservice.config.RabbitMQConfig;
import com.example.orderservice.event.OrderCreatedEvent;

@Service
public class OrderService {

    private final RabbitTemplate rabbitTemplate;

    public OrderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public String createOrder(String product, int quantity) {

        String orderId = UUID.randomUUID().toString();

        OrderCreatedEvent event =
                new OrderCreatedEvent(
                        orderId,
                        product,
                        quantity
                );

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                event
        );

        return orderId;
    }
}