package com.example.flowersystem.api;

import com.example.flowersystem.dto.FlowerDTO;
import com.example.flowersystem.dto.OrderDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FlowerApi {
//    @GET("/v1/orders/{id}")
//    public Call<OrderDTO> getOrderById(@Path("id") Long id);
    @GET("/v1/flowers/{id}")
    public Call<FlowerDTO> getFlower(@Path("id") Long id);
    @GET("/v1/flowers")
    public Call<List<FlowerDTO>> getAllFlowers();
}
