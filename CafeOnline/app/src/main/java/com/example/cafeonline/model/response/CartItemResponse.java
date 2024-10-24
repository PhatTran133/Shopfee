package com.example.cafeonline.model.response;

import java.util.List;

public class CartItemResponse {
    public int Id;
    public int quantity;
    public String variant;
    public String size;
    public String sugar;
    public String iced;
    public List<ToppingResponse> cartItemToppingDTOs;
    public double totalPrice;
    public DrinkResponse drinkDTO;

    public CartItemResponse(int id, int quantity, String variant, String size, String sugar, String iced, List<ToppingResponse> cartItemToppingDTOs, double totalPrice, DrinkResponse drinkDTO) {
        Id = id;
        this.quantity = quantity;
        this.variant = variant;
        this.size = size;
        this.sugar = sugar;
        this.iced = iced;
        this.cartItemToppingDTOs = cartItemToppingDTOs;
        this.totalPrice = totalPrice;
        this.drinkDTO = drinkDTO;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSugar() {
        return sugar;
    }

    public void setSugar(String sugar) {
        this.sugar = sugar;
    }

    public String getIced() {
        return iced;
    }

    public void setIced(String iced) {
        this.iced = iced;
    }

    public List<ToppingResponse> getCartItemToppingDTOs() {
        return cartItemToppingDTOs;
    }

    public void setCartItemToppingDTOs(List<ToppingResponse> cartItemToppingDTOs) {
        this.cartItemToppingDTOs = cartItemToppingDTOs;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public DrinkResponse getDrinkDTO() {
        return drinkDTO;
    }

    public void setDrinkDTO(DrinkResponse drinkDTO) {
        this.drinkDTO = drinkDTO;
    }
}
