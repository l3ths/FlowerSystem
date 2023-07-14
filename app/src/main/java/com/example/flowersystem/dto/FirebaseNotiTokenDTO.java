package com.example.flowersystem.dto;


import java.io.Serializable;

public class FirebaseNotiTokenDTO implements Serializable {
    private String token;

    public FirebaseNotiTokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public FirebaseNotiTokenDTO() {
    }
}
