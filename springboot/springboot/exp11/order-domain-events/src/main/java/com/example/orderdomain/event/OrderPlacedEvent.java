package com.example.orderdomain.event;

import java.time.Instant;

public record OrderPlacedEvent(String eventId, String orderId, String itemName,
                               int quantity, Instant occurredAt) {
}
