package com.example.transaction_service.model;

public class Transaction {

    private int customerId;
    private String type;
    private double amount;

    public Transaction() {
    }

    public Transaction(int customerId, String type, double amount) {
        this.customerId = customerId;
        this.type = type;
        this.amount = amount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}