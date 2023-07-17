package com.example.flowersystem.api;


import com.example.flowersystem.dto.MessageDTO;
import com.example.flowersystem.dto.OrderDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderApi {
    @GET("/v1/orders/{id}")
    public Call<OrderDTO> getOrderById(@Path("id") Long id);
    @POST("/v1/orders/{customerId}")
    public Call<MessageDTO> createOrder(@Path("customerId") Long customerId, @Body OrderDTO orderDTO);
    @GET("/v1/orders/customer/{customerId}")
    public Call<List<OrderDTO>> getAllOrderByCustomerID(@Path("customerId") Long customerId);
    @POST("/v1/orders/prepaid/{customerId}")
    public Call<OrderDTO> createPrepaidOrder(@Path("customerId") Long customerId, @Body OrderDTO orderDTO);
    @PUT("/v1/v1/orders/{orderId}/order-status")
    public Call<MessageDTO> updateOrderStatus(@Path("orderId") long orderId,@Body OrderDTO orderDTO);
}
