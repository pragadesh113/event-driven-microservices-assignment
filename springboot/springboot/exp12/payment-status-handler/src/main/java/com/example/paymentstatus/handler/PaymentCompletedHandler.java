package com.example.paymentstatus.handler;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.paymentstatus.event.PaymentCompletedEvent;
import com.example.paymentstatus.service.OrderService;

@Component
public class PaymentCompletedHandler {
    private final OrderService orderService;

    public PaymentCompletedHandler(OrderService orderService) {
        this.orderService = orderService;
    }

    @EventListener
    public void handle(PaymentCompletedEvent event) {
        orderService.updateStatus(event.orderId(), "PAID");
        System.out.println("PaymentCompletedEvent handled for " + event.orderId());
    }
}
