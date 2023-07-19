package com.example.flowersystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flowersystem.api.OrderApi;
import com.example.flowersystem.api.RetrofitClient;
import com.example.flowersystem.dto.CustomerDTO;
import com.example.flowersystem.dto.MessageDTO;
import com.example.flowersystem.dto.OrderAdapter;
import com.example.flowersystem.dto.OrderDTO;
import com.example.flowersystem.dto.OrderFlower;
import com.example.flowersystem.dto.OrderManagerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderManagerActivity extends AppCompatActivity {
    RecyclerView rvOrder;
    OrderManagerAdapter adapter;
    TextView tvShipping;
    TextView tvShipped;
    TextView tvCanceled;
    ImageView ivBack;
    String STATUS = Constants.OrderStatusNumber.CREATED;
    ImageView ivNotification;
    ImageView ivLogout;
    ImageView ivShopLocate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manager);
        rvOrder = findViewById(R.id.rvOrder);
        tvShipping = findViewById(R.id.tvShipping);
        tvShipped = findViewById(R.id.tvShipped);
        tvCanceled = findViewById(R.id.tvCanceled);
        ivBack = findViewById(R.id.ivBack);
        ivNotification = findViewById(R.id.ivNotification);
        ivLogout = findViewById(R.id.ivPersonal);
        ivShopLocate = findViewById(R.id.ivLove);

        ivShopLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderManagerActivity.this, ShopInformationActivity.class);
                startActivity(intent);
            }
        });

        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderManagerActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderManagerActivity.this, SendNotificationActivity.class);
                startActivity(intent);
            }
        });

        tvShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tvShipping.setBackgroundColor(getResources().getColor(R.color.ligt_orange));
                tvShipping.setBackgroundResource(R.drawable.button_choose_shadow);
                tvShipped.setBackgroundColor(Color.WHITE);
                tvCanceled.setBackgroundColor(Color.WHITE);
                STATUS = Constants.OrderStatusNumber.CREATED;
                onResume();
            }
        });
        tvShipped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tvShipped.setBackgroundColor(getResources().getColor(R.color.ligt_orange));
                tvShipped.setBackgroundResource(R.drawable.button_choose_shadow);
                tvShipping.setBackgroundColor(Color.WHITE);
                tvCanceled.setBackgroundColor(Color.WHITE);
                STATUS = Constants.OrderStatusNumber.SHIPPED;
                onResume();
            }
        });
        tvCanceled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tvCanceled.setBackgroundColor(getResources().getColor(R.color.ligt_orange));
                tvCanceled.setBackgroundResource(R.drawable.button_choose_shadow);
                tvShipped.setBackgroundColor(Color.WHITE);
                tvShipping.setBackgroundColor(Color.WHITE);
                STATUS = Constants.OrderStatusNumber.CANCELLED;
                onResume();
            }
        });

        rvOrder.setLayoutManager(new LinearLayoutManager(OrderManagerActivity.this));
        adapter = new OrderManagerAdapter(OrderManagerActivity.this);
        rvOrder.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveTask();
    }

    private void retrieveTask() {
        Retrofit retrofit = RetrofitClient.getInstance();
        OrderApi orderApi = retrofit.create(OrderApi.class);
        try {
            Call<List<OrderDTO>> call = orderApi.getAllOrdersByStatus(STATUS);
            call.enqueue(new Callback<List<OrderDTO>>() {
                @Override
                public void onResponse(Call<List<OrderDTO>> call, Response<List<OrderDTO>> response) {
                    List<OrderDTO> list = response.body();
                    if (list != null) {
                        ArrayList<OrderFlower> orderFlowerList = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            orderFlowerList.add(new OrderFlower(list.get(i)));
                        }
                        adapter.setTasks(orderFlowerList);
                        if (adapter.getItemCount() == 0) {
                            Toast.makeText(OrderManagerActivity.this, "Đơn hàng trống!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<OrderDTO>> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}