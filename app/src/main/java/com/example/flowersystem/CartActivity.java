package com.example.flowersystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    ImageView ivBack;
    ImageView ivOrders;
    ImageView ivHome;
    ImageView ivNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        rvCart = findViewById(R.id.rvCart);
        tvTotal = findViewById(R.id.tvTotalCart);
        btnOrder = findViewById(R.id.btnOrder);
        ivOrders = findViewById(R.id.ivOrders);
        ivHome = findViewById(R.id.ivHome);
        ivNotification = findViewById(R.id.ivNotification);
        rvCart.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        adapter = new CartAdapter(CartActivity.this);
        rvCart.setAdapter(adapter);
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CartDTO> list = adapter.getTasks();
                if (list == null || list.size() == 0) {
                    Toast.makeText(CartActivity.this, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(CartActivity.this, ConfirmOrderActivity.class);
                    startActivity(intent);
                }
            }
        });

        ivOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, OrdersActivity.class);
                startActivity(intent);
            }
        });

        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, NotificationActivity.class);
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
                    if (total == 0) {
                        Toast.makeText(CartActivity.this, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
                    }
                    tvTotal.setText(total + "");
                }

                @Override
                public void onFailure(Call<List<CartDTO>> call, Throwable t) {
                    Log.d("Error", t.getMessage());
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