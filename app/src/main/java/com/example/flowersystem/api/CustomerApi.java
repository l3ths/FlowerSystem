package com.example.flowersystem.api;

import com.example.flowersystem.dto.CustomerDTO;
import com.example.flowersystem.dto.FirebaseNotiTokenDTO;
import com.example.flowersystem.dto.FlowerDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CustomerApi {
    @GET("/v1/customers/{id}")
    public Call<CustomerDTO> getCustomer(@Path("id") Long id);
    @PUT("/v1/customers/{customerId}")
    public Call<Void> update(@Path("customerId") Long id, @Body CustomerDTO customerDTO);

    @POST("/v1/customers")
    public Call<Void> register(@Body CustomerDTO customerDTO);

    @PUT("/v1/customers/{customerId}/notification-token")
    public Call<Void> updateCustomerNotificationToken(@Path("customerId") Long id, @Body FirebaseNotiTokenDTO firebaseNotiTokenDTO);
}
