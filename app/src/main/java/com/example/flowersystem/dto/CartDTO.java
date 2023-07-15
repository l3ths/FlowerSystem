package com.example.flowersystem.dto;


import java.io.Serializable;

public class CartDTO implements Serializable {
    private Long id;
    private FlowerDTO flowerDTO;
    private int quantity;

    public CartDTO() {
    }

    public CartDTO(FlowerDTO flowerDTO, int quantity) {
        this.flowerDTO = flowerDTO;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FlowerDTO getFlowerDTO() {
        return flowerDTO;
    }

    public void setFlowerDTO(FlowerDTO flowerDTO) {
        this.flowerDTO = flowerDTO;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
