package com.example.cafeonline.model.request;

public class PaymentRequest {
    private String orderType;
    private double amount;
    private String orderDescription;
    private String name;

    public PaymentRequest(String orderType, double amount, String orderDescription, String name) {
        this.orderType = orderType;
        this.amount = amount;
        this.orderDescription = orderDescription;
        this.name = name;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
