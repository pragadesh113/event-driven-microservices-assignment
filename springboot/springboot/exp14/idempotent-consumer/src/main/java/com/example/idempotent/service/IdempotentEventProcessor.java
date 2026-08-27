package com.example.idempotent.service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.example.idempotent.event.OrderEvent;

@Service
public class IdempotentEventProcessor {
    private final Set<String> processedEventIds = ConcurrentHashMap.newKeySet();
    private final AtomicInteger businessUpdates = new AtomicInteger();

    public Result process(OrderEvent event) {
        boolean firstDelivery = processedEventIds.add(event.eventId());
        if (firstDelivery) {
            businessUpdates.incrementAndGet();
            System.out.println("Processed event " + event.eventId());
            return new Result("PROCESSED", businessUpdates.get());
        }
        System.out.println("Ignored duplicate event " + event.eventId());
        return new Result("DUPLICATE_IGNORED", businessUpdates.get());
    }

    public record Result(String result, int businessUpdateCount) { }
}
