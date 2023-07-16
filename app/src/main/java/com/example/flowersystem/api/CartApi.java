package com.example.flowersystem.api;

import com.example.flowersystem.dto.CartDTO;
import com.example.flowersystem.dto.FlowerDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CartApi {
//    @GET("/v1/flowers/{id}")
//    public Call<FlowerDTO> getFlower(@Path("id") Long id);
//    @GET("/v1/flowers")
//    public Call<List<FlowerDTO>> getAllFlowers();
    @GET("/v1/carts/customer/{customerId}")
    public Call<List<CartDTO>> getAllCartByID(@Path("customerId") Long customerId);
    @POST("/v1/carts/customer/{customerId}")
    public Call<CartDTO> addToCart(@Path("customerId") Long customerId,@Body CartDTO cartDTO);
    @DELETE("/v1/carts/{id}")
    public Call<CartDTO> deleteCart(@Path("id") Long id);
    @DELETE("/v1/carts/customer/{customerId}")
    public Call<CartDTO> deleteAllCart(@Path("customerId") Long customerId);
}
