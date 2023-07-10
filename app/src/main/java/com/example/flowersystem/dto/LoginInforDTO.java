package com.example.flowersystem.dto;


import java.io.Serializable;

public class LoginInforDTO implements Serializable {
    private String phone;
    private String password;

    public LoginInforDTO() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
