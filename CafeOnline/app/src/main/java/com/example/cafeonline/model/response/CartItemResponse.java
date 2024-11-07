package com.example.cafeonline.model.response;

import java.util.List;

public class CartItemResponse {
    public int id;
    private int quantity;
    private String variant;
    private String size;
    private String sugar;
    private String iced;
    private String note;
    private List<CartItemToppingResponse> cartItemToppingDTOs;
    private int unitPrice;
    private int totalPrice;
    private DrinkResponse drinkDTO;

    public CartItemResponse(int id, int quantity, String variant, String size, String sugar, String iced, String note, List<CartItemToppingResponse> cartItemToppingDTOs, int unitPrice, int totalPrice, DrinkResponse drinkDTO) {
        this.id = id;
        this.quantity = quantity;
        this.variant = variant;
        this.size = size;
        this.sugar = sugar;
        this.iced = iced;
        this.note = note;
        this.cartItemToppingDTOs = cartItemToppingDTOs;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.drinkDTO = drinkDTO;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<CartItemToppingResponse> getCartItemToppingDTOs() {
        return cartItemToppingDTOs;
    }

    public void setCartItemToppingDTOs(List<CartItemToppingResponse> cartItemToppingDTOs) {
        this.cartItemToppingDTOs = cartItemToppingDTOs;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public DrinkResponse getDrinkDTO() {
        return drinkDTO;
    }

    public void setDrinkDTO(DrinkResponse drinkDTO) {
        this.drinkDTO = drinkDTO;
    }
}
