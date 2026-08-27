package com.example.saga.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class SagaStateStore {
    private final Map<String, String> states = new ConcurrentHashMap<>();

    public void set(String orderId, String state) {
        states.put(orderId, state);
        System.out.println(orderId + " -> " + state);
    }

    public String get(String orderId) {
        return states.get(orderId);
    }
}
