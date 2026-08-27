package com.example.orderdomain.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderdomain.event.OrderPlacedEvent;
import com.example.orderdomain.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place")
    public OrderPlacedEvent place(@RequestParam String item, @RequestParam int quantity) {
        return orderService.placeOrder(item, quantity);
    }
}
