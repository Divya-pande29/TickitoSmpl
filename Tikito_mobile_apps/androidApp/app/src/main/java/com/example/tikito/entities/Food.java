package com.example.tikito.entities;

import java.io.Serializable;

public class Food implements Serializable
{
    private Long foodId;

    private String foodName;

    private String description;

    private String imageUrl;

    private Double price;

    private boolean available;

    private int quantity = 0;

    public Food(Long foodId, String foodName, String description, String imageUrl, Double price, boolean available)
    {
        this.foodId = foodId;
        this.foodName = foodName;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.available = available;
    }

    public Food()
    {

    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
