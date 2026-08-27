package com.example.saga.event;

public final class SagaEvents {
    private SagaEvents() { }

    public record OrderCreated(String orderId, double amount, boolean deliveryAvailable) { }
    public record PaymentCompleted(String orderId, boolean deliveryAvailable) { }
    public record PaymentFailed(String orderId, String reason) { }
    public record DeliveryAssigned(String orderId, String deliveryPartner) { }
    public record DeliveryFailed(String orderId, String reason) { }
}
