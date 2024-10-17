package com.example.cafeonline.model.request;

import java.math.BigDecimal;

public class DrinkRequestModel {
   private int id;
   private String name;
   private BigDecimal price;
   private String categoryName;
   private String image;

    public DrinkRequestModel() {
    }

    public DrinkRequestModel(int id, String name, BigDecimal price, String categoryName, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryName = categoryName;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
