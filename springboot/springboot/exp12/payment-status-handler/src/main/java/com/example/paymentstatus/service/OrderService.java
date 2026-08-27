package com.example.paymentstatus.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.example.paymentstatus.event.PaymentCompletedEvent;
import com.example.paymentstatus.model.Order;

@Service
public class OrderService {
    private final Map<String, Order> orders = new ConcurrentHashMap<>();
    private final ApplicationEventPublisher publisher;

    public OrderService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public Order create(String orderId, String itemName) {
        Order order = new Order(orderId, itemName, "PAYMENT_PENDING");
        orders.put(orderId, order);
        return order;
    }

    public void paymentCompleted(PaymentCompletedEvent event) {
        publisher.publishEvent(event);
    }

    public Order updateStatus(String orderId, String status) {
        return orders.computeIfPresent(orderId, (id, order) -> order.withStatus(status));
    }

    public Order find(String orderId) {
        return orders.get(orderId);
    }
}
