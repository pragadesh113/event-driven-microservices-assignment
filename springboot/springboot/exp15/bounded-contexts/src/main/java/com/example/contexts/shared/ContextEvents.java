package com.example.contexts.shared;

public final class ContextEvents {
    private ContextEvents() { }
    public record OrderPlaced(String orderId, double amount) { }
    public record PaymentCompleted(String orderId, String paymentId) { }
}
