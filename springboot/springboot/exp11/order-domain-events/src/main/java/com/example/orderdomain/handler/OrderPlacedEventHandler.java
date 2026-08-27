package com.example.orderdomain.handler;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.orderdomain.event.OrderPlacedEvent;

@Component
public class OrderPlacedEventHandler {
    @EventListener
    public void handle(OrderPlacedEvent event) {
        System.out.println("OrderPlacedEvent published: " + event);
    }
}
