package com.example.contexts.order;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.example.contexts.shared.ContextEvents.OrderPlaced;
import com.example.contexts.shared.ContextEvents.PaymentCompleted;

@Service
public class OrderContextService {
    private final Map<String, String> orders = new ConcurrentHashMap<>();
    private final ApplicationEventPublisher publisher;

    public OrderContextService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void place(String orderId, double amount) {
        orders.put(orderId, "PLACED");
        publisher.publishEvent(new OrderPlaced(orderId, amount));
    }

    @EventListener
    public void onPayment(PaymentCompleted event) {
        orders.put(event.orderId(), "PAID");
    }

    public String status(String orderId) {
        return orders.get(orderId);
    }
}
