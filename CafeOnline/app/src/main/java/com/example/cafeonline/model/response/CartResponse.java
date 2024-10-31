package com.example.cafeonline.model.response;

import java.util.List;

public class CartResponse {
    public int id;
    public int userId;
    public double totalPrice;
    public List<CartItemResponse> cartItems;


    public CartResponse(int id, int userId, double totalPrice, List<CartItemResponse> cartItems) {
        this.id = id;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.cartItems = cartItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartItemResponse> getCartItems() {
        return cartItems;
    }

    public void setCartItemToppingDTOs(List<CartItemResponse> cartItems) {
        this.cartItems = cartItems;
    }
}
