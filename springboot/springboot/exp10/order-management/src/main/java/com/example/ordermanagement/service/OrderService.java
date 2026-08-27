package com.example.ordermanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ordermanagement.model.Order;

@Service
public class OrderService {

    private final List<Order> orders = new ArrayList<>();

    private Long nextId = 1L;

    public Order createOrder(Order order) {

        order.setId(nextId++);

        orders.add(order);

        return order;
    }

    public List<Order> getAllOrders() {
        return orders;
    }

    public Order getOrderById(Long id) {

        return orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}