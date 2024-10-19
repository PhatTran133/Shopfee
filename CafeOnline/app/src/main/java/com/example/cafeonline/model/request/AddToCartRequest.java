package com.example.cafeonline.model.request;

import java.util.List;

public class AddToCartRequest {
    private int userId;
    private int drinkId;
    private int quantity;
    private int total;
    private String variant;
    private String size;
    private String sugar;
    private String iced;
    private String note;
    private List<Topping> toppings;

    public static class Topping {
        private int id;

        public Topping(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public AddToCartRequest(int userId, int drinkId, int quantity, int total, String variant, String size, String sugar, String iced, String note, List<Topping> toppings) {
        this.userId = userId;
        this.drinkId = drinkId;
        this.quantity = quantity;
        this.total = total;
        this.variant = variant;
        this.size = size;
        this.sugar = sugar;
        this.iced = iced;
        this.note = note;
        this.toppings = toppings;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(int drinkId) {
        this.drinkId = drinkId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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

    public List<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(List<Topping> toppings) {
        this.toppings = toppings;
    }
}

