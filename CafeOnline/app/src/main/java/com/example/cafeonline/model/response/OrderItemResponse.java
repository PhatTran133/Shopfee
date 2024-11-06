package com.example.cafeonline.model.response;

import java.util.List;

public class OrderItemResponse {
    public int id;
    private int quantity;
    private String variant;
    private String size;
    private String sugar;
    private String iced;
    private String note;
    private List<CartItemToppingResponse> orderItemToppingDTOs;
    private int totalPrice;
    private DrinkResponse drinkDTO;

    public OrderItemResponse(int id, int quantity, String variant, String size, String sugar, String iced, String note, List<CartItemToppingResponse> orderItemToppingDTOs, int totalPrice, DrinkResponse drinkDTO) {
        this.id = id;
        this.quantity = quantity;
        this.variant = variant;
        this.size = size;
        this.sugar = sugar;
        this.iced = iced;
        this.note = note;
        this.orderItemToppingDTOs = orderItemToppingDTOs;
        this.totalPrice = totalPrice;
        this.drinkDTO = drinkDTO;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<CartItemToppingResponse> getOrderItemToppingDTOs() {
        return orderItemToppingDTOs;
    }

    public void setOrderItemToppingDTOs(List<CartItemToppingResponse> orderItemToppingDTOs) {
        this.orderItemToppingDTOs = orderItemToppingDTOs;
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
