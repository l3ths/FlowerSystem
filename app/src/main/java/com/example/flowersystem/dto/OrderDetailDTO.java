package com.example.flowersystem.dto;


import java.io.Serializable;

public class OrderDetailDTO implements Serializable {
    private Long orderId;
    private FlowerDTO flowerDTO;
    private double unitPrice;
    private int quantity;

    public OrderDetailDTO() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public FlowerDTO getFlowerDTO() {
        return flowerDTO;
    }

    public void setFlowerDTO(FlowerDTO flowerDTO) {
        this.flowerDTO = flowerDTO;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
