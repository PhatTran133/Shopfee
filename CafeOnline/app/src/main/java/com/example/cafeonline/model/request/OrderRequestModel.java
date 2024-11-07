package com.example.cafeonline.model.request;

public class OrderRequestModel {
    private int userId;
    private int cartId;
    private PaymentOrderRequest paymentRequest;

    public OrderRequestModel(int userId, int cartId, PaymentOrderRequest paymentRequest) {
        this.userId = userId;
        this.cartId = cartId;
        this.paymentRequest = paymentRequest;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public PaymentOrderRequest getPaymentRequest() {
        return paymentRequest;
    }

    public void setPaymentRequest(PaymentOrderRequest paymentRequest) {
        this.paymentRequest = paymentRequest;
    }
}


