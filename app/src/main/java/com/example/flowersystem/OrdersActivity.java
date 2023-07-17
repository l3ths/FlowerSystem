package com.example.flowersystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flowersystem.api.CartApi;
import com.example.flowersystem.api.OrderApi;
import com.example.flowersystem.api.RetrofitClient;
import com.example.flowersystem.dto.CartAdapter;
import com.example.flowersystem.dto.CartDTO;
import com.example.flowersystem.dto.CustomerDTO;
import com.example.flowersystem.dto.MessageDTO;
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
    TextView tvShipping;
    TextView tvShipped;
    TextView tvCanceled;
    ImageView ivBack;
    String STATUS = Constants.OrderStatusNumber.CREATED;
    ImageView ivHome;
    ImageView ivNotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        rvOrder = findViewById(R.id.rvOrder);
        tvShipping = findViewById(R.id.tvShipping);
        tvShipped = findViewById(R.id.tvShipped);
        tvCanceled = findViewById(R.id.tvCanceled);
        ivBack = findViewById(R.id.ivBack);
        ivHome = findViewById(R.id.ivHome);
        ivNotification = findViewById(R.id.ivNotification);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrdersActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrdersActivity.this, NotificationActivity.class);
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
                        if (TextUtils.equals(list.get(i).getOrderStatus(), STATUS)) {
                            orderFlowerList.add(new OrderFlower(list.get(i)));
                        }
                    }
                    adapter.setTasks(orderFlowerList);
                    if (adapter.getItemCount() == 0) {
                        Toast.makeText(OrdersActivity.this, "Đơn hàng trống!", Toast.LENGTH_SHORT).show();
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
    public void cancelOrder(OrderFlower orderFlower){
        new AlertDialog.Builder(OrdersActivity.this)
                .setTitle("Huỷ đơn hàng")
                .setMessage("Huỷ đơn " + orderFlower.getOrderDTO().getId() + " ?")

                .setPositiveButton("XÁC NHẬN", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Retrofit retrofit = RetrofitClient.getInstance();
                        OrderApi orderApi = retrofit.create(OrderApi.class);
                        OrderDTO dto = new OrderDTO();
                        dto.setOrderStatus(Constants.OrderStatusNumber.CANCELLED);
                        Call<MessageDTO> call = orderApi.updateOrderStatus(orderFlower.getOrderDTO().getId(), dto);
                        call.enqueue(new Callback<MessageDTO>() {
                            @Override
                            public void onResponse(Call<MessageDTO> call, Response<MessageDTO> response) {
                            }

                            @Override
                            public void onFailure(Call<MessageDTO> call, Throwable t) {
                                adapter.notifyDataSetChanged();
                                onResume();
                                Toast.makeText(OrdersActivity.this, "Huỷ thành công!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("HUỶ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}