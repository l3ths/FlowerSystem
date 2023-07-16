package com.example.flowersystem.api;


import com.example.flowersystem.dto.OrderDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderApi {
    @GET("/v1/orders/{id}")
    public Call<OrderDTO> getOrderById(@Path("id") Long id);
    @POST("/v1/orders/{customerId}")
    public Call<OrderDTO> createOrder(@Path("customerId") Long customerId, @Body OrderDTO orderDTO);
}
