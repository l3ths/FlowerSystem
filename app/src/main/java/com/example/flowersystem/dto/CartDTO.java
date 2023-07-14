package com.example.flowersystem.dto;


import java.io.Serializable;

public class CartDTO implements Serializable {
    private Long id;
    private FlowerDTO fLowerDTO;
    private int quantity;

    public CartDTO() {
    }

    public CartDTO(FlowerDTO fLowerDTO, int quantity) {
        this.fLowerDTO = fLowerDTO;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FlowerDTO getfLowerDTO() {
        return fLowerDTO;
    }

    public void setfLowerDTO(FlowerDTO fLowerDTO) {
        this.fLowerDTO = fLowerDTO;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
