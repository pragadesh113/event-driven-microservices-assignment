package com.example.paymentstatus.model;

public record Order(String orderId, String itemName, String status) {
    public Order withStatus(String newStatus) {
        return new Order(orderId, itemName, newStatus);
    }
}
