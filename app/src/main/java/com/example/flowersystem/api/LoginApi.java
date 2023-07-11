package com.example.flowersystem.api;

import com.example.flowersystem.dto.JwtTokenDTO;
import com.example.flowersystem.dto.LoginInforDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApi {
    @POST("/v1/login")
    public Call<JwtTokenDTO> login(@Body LoginInforDTO loginInforDTO);
}
