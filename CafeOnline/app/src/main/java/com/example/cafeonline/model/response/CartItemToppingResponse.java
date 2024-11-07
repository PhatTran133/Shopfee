package com.example.cafeonline.model.response;

public class CartItemToppingResponse {
    private ToppingResponse topping;

    public CartItemToppingResponse(ToppingResponse topping) {
        this.topping = topping;
    }

    public ToppingResponse getTopping() {
        return topping;
    }

    public void setTopping(ToppingResponse topping) {
        this.topping = topping;
    }
}