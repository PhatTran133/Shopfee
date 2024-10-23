package com.example.cafeonline.model.response;

import java.util.Date;
import java.util.List;

public class CartResponse {
    public int Id;
    public int UserId;
    public double TotalPrice;
    public List<CartToppingResponse> Options;

    public CartResponse(int id, int userId, double totalPrice, List<CartToppingResponse> options) {
        Id = id;
        UserId = userId;
        TotalPrice = totalPrice;
        Options = options;
    }

    public List<CartToppingResponse> getOptions() {
        return Options;
    }

    public void setOptions(List<CartToppingResponse> options) {
        Options = options;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
