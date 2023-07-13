package com.example.flowersystem.api;

import com.example.flowersystem.dto.CustomerDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CustomerApi {
    @POST("/v1/customers")
    public Call<Void> register(@Body CustomerDTO customerDTO);
}
