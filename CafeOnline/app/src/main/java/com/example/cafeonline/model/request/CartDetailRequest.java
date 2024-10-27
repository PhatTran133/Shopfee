package com.example.cafeonline.model.request;

public class CartDetailRequest {
    private int userId;

    public CartDetailRequest(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
