package com.example.cafeonline.model.response;

import java.math.BigDecimal;

public class ToppingResponse {
    private int id;
    private String name;
    private double price;
    private boolean selected; // Trường mới để theo dõi trạng thái chọn topping

    // Getter và Setter cho id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter và Setter cho name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter và Setter cho price
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Getter và Setter cho selected
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

