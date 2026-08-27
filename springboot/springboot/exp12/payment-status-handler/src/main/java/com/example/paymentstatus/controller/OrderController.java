package com.example.paymentstatus.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.paymentstatus.event.PaymentCompletedEvent;
import com.example.paymentstatus.model.Order;
import com.example.paymentstatus.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order create(@RequestParam String orderId, @RequestParam String item) {
        return orderService.create(orderId, item);
    }

    @PostMapping("/{orderId}/payment-completed")
    public Order completePayment(@PathVariable String orderId, @RequestParam double amount) {
        orderService.paymentCompleted(new PaymentCompletedEvent(UUID.randomUUID().toString(), orderId, amount));
        return orderService.find(orderId);
    }

    @GetMapping("/{orderId}")
    public Order find(@PathVariable String orderId) {
        return orderService.find(orderId);
    }
}
