package com.example.cafeonline.model.request;

public class CartItemRequestModel {

    private int quantity;
    private String variant;
    private String size;
    private String sugar;
    private String iced;
    private String note;
    private int totalPrice;

    public CartItemRequestModel() {
    }

    public CartItemRequestModel(int quantity, String variant, String size, String sugar, String iced, String note, int totalPrice) {
        this.quantity = quantity;
        this.variant = variant;
        this.size = size;
        this.sugar = sugar;
        this.iced = iced;
        this.note = note;
        this.totalPrice = totalPrice;
    }

    public CartItemRequestModel(int quantity) {
        this.quantity = quantity;
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

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
