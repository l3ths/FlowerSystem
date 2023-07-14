package com.example.flowersystem.api;


import com.example.flowersystem.dto.ShopProfileDTO;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ShopProfileApi {
    @GET("/v1/shop-profile")
    public Call<ShopProfileDTO> getShopProfile();
}
