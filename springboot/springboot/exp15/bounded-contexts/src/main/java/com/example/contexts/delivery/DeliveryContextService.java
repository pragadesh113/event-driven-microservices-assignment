package com.example.contexts.delivery;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.example.contexts.shared.ContextEvents.PaymentCompleted;

@Service
public class DeliveryContextService {
    private final Map<String, String> deliveries = new ConcurrentHashMap<>();

    @EventListener
    public void onPaymentCompleted(PaymentCompleted event) {
        deliveries.put(event.orderId(), "ASSIGNED:Partner-01");
    }

    public String status(String orderId) {
        return deliveries.get(orderId);
    }
}
