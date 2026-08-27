package com.example.contexts.payment;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.example.contexts.shared.ContextEvents.OrderPlaced;
import com.example.contexts.shared.ContextEvents.PaymentCompleted;

@Service
public class PaymentContextService {
    private final Map<String, String> payments = new ConcurrentHashMap<>();
    private final ApplicationEventPublisher publisher;

    public PaymentContextService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @EventListener
    public void onOrderPlaced(OrderPlaced event) {
        String paymentId = UUID.randomUUID().toString();
        payments.put(event.orderId(), "COMPLETED:" + paymentId);
        publisher.publishEvent(new PaymentCompleted(event.orderId(), paymentId));
    }

    public String status(String orderId) {
        return payments.get(orderId);
    }
}
