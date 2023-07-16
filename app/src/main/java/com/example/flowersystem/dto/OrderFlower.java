package com.example.flowersystem.dto;

import com.example.flowersystem.api.OrderDetailApi;
import com.example.flowersystem.api.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderFlower {
    private OrderDTO orderDTO;
    private List<OrderDetailDTO> list;

    public OrderFlower(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
        Retrofit retrofit = RetrofitClient.getInstance();
        OrderDetailApi orderDetailApi = retrofit.create(OrderDetailApi.class);
        Call<List<OrderDetailDTO>> call = orderDetailApi.getOrderDetails(orderDTO.getId());
        call.enqueue(new Callback<List<OrderDetailDTO>>() {
            @Override
            public void onResponse(Call<List<OrderDetailDTO>> call, Response<List<OrderDetailDTO>> response) {
                if (response.isSuccessful()){
                    list = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<OrderDetailDTO>> call, Throwable t) {

            }
        });
    }

    public OrderDTO getOrderDTO() {
        return orderDTO;
    }

    public void setOrderDTO(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

    public List<OrderDetailDTO> getList() {
        return list;
    }

    public void setList(List<OrderDetailDTO> list) {
        this.list = list;
    }
}
