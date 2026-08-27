package com.example.saga.controller;

import java.util.Map;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.saga.event.SagaEvents.OrderCreated;
import com.example.saga.service.SagaStateStore;

@RestController
@RequestMapping("/saga")
public class SagaController {
    private final ApplicationEventPublisher publisher;
    private final SagaStateStore states;

    public SagaController(ApplicationEventPublisher publisher, SagaStateStore states) {
        this.publisher = publisher;
        this.states = states;
    }

    @PostMapping("/start")
    public Map<String, String> start(@RequestParam String orderId,
                                     @RequestParam double amount,
                                     @RequestParam(defaultValue = "true") boolean deliveryAvailable) {
        states.set(orderId, "ORDER_CREATED");
        publisher.publishEvent(new OrderCreated(orderId, amount, deliveryAvailable));
        return Map.of("orderId", orderId, "finalState", states.get(orderId));
    }
}
