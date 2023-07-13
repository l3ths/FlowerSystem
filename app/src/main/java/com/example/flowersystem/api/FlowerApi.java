package com.example.flowersystem.api;

import com.example.flowersystem.dto.FlowerDTO;
import com.example.flowersystem.dto.OrderDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FlowerApi {
//    @GET("/v1/orders/{id}")
//    public Call<OrderDTO> getOrderById(@Path("id") Long id);
    @GET
    public Call<FlowerDTO[]> getAllFlowers();
}
