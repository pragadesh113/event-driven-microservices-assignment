package com.example.orderservice.model;

public class Order {

    private int id;
    private int customerId;
    private String itemName;
    private double amount;
    private String status;

    public Order() {
    }

    public Order(int id, int customerId, String itemName,
                 double amount, String status) {

        this.id = id;
        this.customerId = customerId;
        this.itemName = itemName;
        this.amount = amount;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
