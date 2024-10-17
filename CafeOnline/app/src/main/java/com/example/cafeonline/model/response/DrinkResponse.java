package com.example.cafeonline.model.response;

import java.math.BigDecimal;
import java.util.List;

public class DrinkResponse {
    private int id;
    private String name;
    private String description;
    private double price;
    private String categoryName;
    private String image;
    private List<String> toppingNames;

    public DrinkResponse(int id, String name, String description, double price, String categoryName, List<String> toppingNames, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryName = categoryName;
        this.toppingNames = toppingNames;
        this.image=image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<String> getToppingNames() {
        return toppingNames;
    }

    public void setToppingNames(List<String> toppingNames) {
        this.toppingNames = toppingNames;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
