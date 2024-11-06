package com.example.cafeonline.model.request;

public class PaymentRequest {
    private int userId;
    private int orderId;
    private String orderType;
    private double amount;
    private String orderDescription;
    private String name;

    public PaymentRequest(int userId, int orderId, String orderType, double amount, String orderDescription, String name) {
        this.userId = userId;
        this.orderId = orderId;
        this.orderType = orderType;
        this.amount = amount;
        this.orderDescription = orderDescription;
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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
