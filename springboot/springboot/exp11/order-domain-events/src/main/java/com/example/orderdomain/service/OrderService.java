package com.example.orderdomain.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.example.orderdomain.event.OrderPlacedEvent;

@Service
public class OrderService {
    private final ApplicationEventPublisher publisher;

    public OrderService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public OrderPlacedEvent placeOrder(String itemName, int quantity) {
        OrderPlacedEvent event = new OrderPlacedEvent(
                UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                itemName, quantity, Instant.now());
        publisher.publishEvent(event);
        return event;
    }
}
