package com.example.paymentstatus.event;

public record PaymentCompletedEvent(String eventId, String orderId, double amount) {
}
