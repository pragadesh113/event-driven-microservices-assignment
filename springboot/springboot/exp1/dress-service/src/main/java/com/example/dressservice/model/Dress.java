package com.example.dressservice.model;

public class Dress {

    private int id;
    private String name;
    private String category;
    private double price;
    private String size;

    public Dress() {
    }

    public Dress(int id, String name, String category, double price, String size) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}