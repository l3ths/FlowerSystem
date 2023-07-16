package com.example.flowersystem.api;

import com.example.flowersystem.dto.OrderDetailDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OrderDetailApi {
    @GET("/v1/orders/{id}/order-details")
    public Call<List<OrderDetailDTO>> getOrderDetails(@Path("id") Long id);
}
