package com.example.flowersystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.flowersystem.api.CartApi;
import com.example.flowersystem.api.FlowerApi;
import com.example.flowersystem.api.RetrofitClient;
import com.example.flowersystem.dto.CartAdapter;
import com.example.flowersystem.dto.CartDTO;
import com.example.flowersystem.dto.CustomerDTO;
import com.example.flowersystem.dto.FlowerDTO;
import com.example.flowersystem.dto.OrderDetailDTO;
import com.example.flowersystem.dto.SearchFlowerAdapter;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CartActivity extends AppCompatActivity {
    RecyclerView rvCart;
    TextView tvTotal;
    CartAdapter adapter;
    CustomerDTO CUSTOMER = Constants.LOGGED_IN_CUSTOMER;
    Button btnOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        rvCart = findViewById(R.id.rvCart);
        tvTotal = findViewById(R.id.tvTotalCart);
        btnOrder = findViewById(R.id.btnOrder);
String a = Constants.OrderStatusString.CANCELLED;
        rvCart.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        adapter = new CartAdapter(CartActivity.this);
        rvCart.setAdapter(adapter);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, ConfirmOrderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveTask();
    }

    private void retrieveTask() {
        Retrofit retrofit = RetrofitClient.getInstance();
        CartApi cartApi = retrofit.create(CartApi.class);
        try {
            Call<List<CartDTO>> call = cartApi.getAllCartByID(CUSTOMER.getId());
            call.enqueue(new Callback<List<CartDTO>>() {
                @Override
                public void onResponse(Call<List<CartDTO>> call, Response<List<CartDTO>> response) {
                    List<CartDTO> list = response.body();
                    adapter.setTasks(list);
                    double total = 0;
                    for (int i = 0; i < list.size(); i++) {
                        total += list.get(i).getQuantity() * list.get(i).getFlowerDTO().getUnitPrice();
                    }
                    tvTotal.setText(total + "");
                }

                @Override
                public void onFailure(Call<List<CartDTO>> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calculateTotal() {
        List<CartDTO> list = adapter.getTasks();
        if (list != null) {
            double total = 0;
            for (int i = 0; i < list.size(); i++) {
                total += list.get(i).getQuantity() * list.get(i).getFlowerDTO().getUnitPrice();
            }
            tvTotal.setText(total + "");
        }
    }
}