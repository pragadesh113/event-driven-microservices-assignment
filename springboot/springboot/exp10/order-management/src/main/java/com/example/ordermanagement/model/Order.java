package com.example.ordermanagement.model;

import java.util.List;

public class Order {

    private Long id;
    private String customerName;
    private Address address;
    private List<OrderItem> items;

    public Order() {
    }

    public Order(Long id, String customerName, Address address, List<OrderItem> items) {
        this.id = id;
        this.customerName = customerName;
        this.address = address;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}