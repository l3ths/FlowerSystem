package com.example.flowersystem.dto;

import java.io.Serializable;

public class JwtTokenDTO implements Serializable {
    private String token;
    private CustomerDTO customer;

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customer= customerDTO;
    }

    public JwtTokenDTO(String token) {
        this.token = token;
    }

    public JwtTokenDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
