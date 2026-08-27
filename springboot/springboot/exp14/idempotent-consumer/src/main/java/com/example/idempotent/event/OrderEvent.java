package com.example.idempotent.event;

public record OrderEvent(String eventId, String orderId, String eventType) {
}
