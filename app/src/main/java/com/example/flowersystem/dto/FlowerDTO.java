package com.example.flowersystem.dto;

import java.io.Serializable;

public class FlowerDTO implements Serializable {
    private Long id;
    private String category;
    private String flowerName;
    private String flowerDescription;
    private double unitPrice;
    private int stock;
    private String flowerStatus;
    private String supplier;
    private String image;

    public Long getId() {
        return id;
    }

    public FlowerDTO() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFlowerName() {
        return flowerName;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
    }

    public String getFlowerDescription() {
        return flowerDescription;
    }

    public void setFlowerDescription(String flowerDescription) {
        this.flowerDescription = flowerDescription;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getFlowerStatus() {
        return flowerStatus;
    }

    public void setFlowerStatus(String flowerStatus) {
        this.flowerStatus = flowerStatus;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
