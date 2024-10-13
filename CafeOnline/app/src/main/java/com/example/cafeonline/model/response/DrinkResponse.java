package com.example.cafeonline.model.response;

import java.math.BigDecimal;
import java.util.List;

public class DrinkResponse {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private String categoryName;
    private List<String> toppingNames;

    public DrinkResponse(int id, String name, String description, BigDecimal price, String categoryName, List<String> toppingNames) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryName = categoryName;
        this.toppingNames = toppingNames;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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
}
