package com.example.cafeonline.model;

public class PaymentMethod {
    private String name;
    private String description;

    public PaymentMethod(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}