package com.example.flowersystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.flowersystem.api.CartApi;
import com.example.flowersystem.api.OrderApi;
import com.example.flowersystem.api.RetrofitClient;
import com.example.flowersystem.dto.CartAdapter;
import com.example.flowersystem.dto.CartDTO;
import com.example.flowersystem.dto.CustomerDTO;
import com.example.flowersystem.dto.OrderAdapter;
import com.example.flowersystem.dto.OrderDTO;
import com.example.flowersystem.dto.OrderFlower;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrdersActivity extends AppCompatActivity {
    RecyclerView rvOrder;
    OrderAdapter adapter;
    CustomerDTO CUSTOMER = Constants.LOGGED_IN_CUSTOMER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        rvOrder = findViewById(R.id.rvOrder);
        rvOrder.setLayoutManager(new LinearLayoutManager(OrdersActivity.this));
        adapter = new OrderAdapter(OrdersActivity.this);
        rvOrder.setAdapter(adapter);
    }
    protected void onResume() {
        super.onResume();
        retrieveTask();
    }

    private void retrieveTask() {
        Retrofit retrofit = RetrofitClient.getInstance();
        OrderApi orderApi = retrofit.create(OrderApi.class);
        try {
            Call<List<OrderDTO>> call = orderApi.getAllOrderByCustomerID(CUSTOMER.getId());
            call.enqueue(new Callback<List<OrderDTO>>() {
                @Override
                public void onResponse(Call<List<OrderDTO>> call, Response<List<OrderDTO>> response) {
                    List<OrderDTO> list = response.body();
                    ArrayList<OrderFlower> orderFlowerList = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        orderFlowerList.add(new OrderFlower(list.get(i)));
                    }
                    adapter.setTasks(orderFlowerList);
                    if (adapter.getItemCount()==0){
                        Toast.makeText(OrdersActivity.this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<OrderDTO>> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}