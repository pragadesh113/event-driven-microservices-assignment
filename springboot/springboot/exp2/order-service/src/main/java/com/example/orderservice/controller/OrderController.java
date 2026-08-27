package com.example.orderservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/info")
    public String serviceInfo() {

        return "Online Food Delivery - Order Service is running";
    }

    @GetMapping("/orders")
    public List<Order> getAllOrders() {

        return orderService.getAllOrders();
    }

    @GetMapping("/orders/{id}")
    public Order getOrderById(@PathVariable int id) {

        return orderService.getOrderById(id);
    }

    @PostMapping("/orders")
    public Order addOrder(@RequestBody Order order) {

        return orderService.addOrder(order);
    }

    @PutMapping("/orders/{id}")
    public Order updateOrder(
            @PathVariable int id,
            @RequestBody Order order) {

        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/orders/{id}")
    public String deleteOrder(@PathVariable int id) {

        boolean deleted = orderService.deleteOrder(id);

        if (deleted) {
            return "Order deleted successfully";
        }

        return "Order not found";
    }
}
