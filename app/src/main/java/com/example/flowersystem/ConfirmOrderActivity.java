package com.example.flowersystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.flowersystem.api.CartApi;
import com.example.flowersystem.api.OrderApi;
import com.example.flowersystem.api.RetrofitClient;
import com.example.flowersystem.dto.CartAdapter;
import com.example.flowersystem.dto.CartDTO;
import com.example.flowersystem.dto.ConfirmOrderAdapter;
import com.example.flowersystem.dto.CustomerDTO;
import com.example.flowersystem.dto.MessageDTO;
import com.example.flowersystem.dto.OrderDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConfirmOrderActivity extends AppCompatActivity {
    RecyclerView rvConfirm;
    TextView tvTotal;
    ImageView ivCustomer;
    TextView tvCustomerName;
    TextView tvCustomerPhone;
    TextView tvCustomerAddress;
    ConfirmOrderAdapter adapter;
    CustomerDTO CUSTOMER = Constants.LOGGED_IN_CUSTOMER;
    Button btnConfirmOrder;
    RadioButton rbPostPaid;
    RadioButton rbPrePaid;
    ImageView ivBack;
    ImageView ivOrders;
    ImageView ivHome;
    ImageView ivNotification;
    ImageView ivEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        rvConfirm = findViewById(R.id.rvConfirmOrder);
        tvTotal = findViewById(R.id.tvConfirmTotal);
        btnConfirmOrder = findViewById(R.id.btnConfirmOrder);
        ivCustomer = findViewById(R.id.ivCustomer);
        tvCustomerName = findViewById(R.id.tvCustomerName);
        tvCustomerAddress = findViewById(R.id.tvCustomerAddress);
        tvCustomerPhone = findViewById(R.id.tvCustomerPhone);
        rbPostPaid = findViewById(R.id.rbPostPaid);
        rbPrePaid = findViewById(R.id.rbPrePaid);
        ivBack = findViewById(R.id.ivBack);
        ivOrders = findViewById(R.id.ivOrders);
        ivHome = findViewById(R.id.ivHome);
        ivNotification = findViewById(R.id.ivNotification);
        ivEdit = findViewById(R.id.imageView14);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ivOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmOrderActivity.this, OrdersActivity.class);
                startActivity(intent);
            }
        });

        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmOrderActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmOrderActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmOrderActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        rvConfirm.setLayoutManager(new LinearLayoutManager(ConfirmOrderActivity.this));
        adapter = new ConfirmOrderAdapter(ConfirmOrderActivity.this);
        rvConfirm.setAdapter(adapter);

        btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = RetrofitClient.getInstance();
                OrderApi orderApi = retrofit.create(OrderApi.class);
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setOrderStatus(Constants.OrderStatusNumber.CREATED);
                if (rbPostPaid.isChecked()) {
                    orderDTO.setPaymentMethod("COD");
                    try {
                        Call<MessageDTO> call = orderApi.createOrder(CUSTOMER.getId(),orderDTO);
                        call.enqueue(new Callback<MessageDTO>() {
                            @Override
                            public void onResponse(Call<MessageDTO> call, Response<MessageDTO> response) {
                                Toast.makeText(ConfirmOrderActivity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ConfirmOrderActivity.this, SearchActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<MessageDTO> call, Throwable t) {
                                Toast.makeText(ConfirmOrderActivity.this, "Fail!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (rbPrePaid.isChecked()) {
                    orderDTO.setPaymentMethod("PREPAID");
                    try {
                        Call<OrderDTO> call = orderApi.createPrepaidOrder(CUSTOMER.getId(), orderDTO);
                        call.enqueue(new Callback<OrderDTO>() {
                            @Override
                            public void onResponse(Call<OrderDTO> call, Response<OrderDTO> response) {
//                                Toast.makeText(ConfirmOrderActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                                //Choose bank
                                Intent intent = new Intent(ConfirmOrderActivity.this, PaymentMethodActivity.class);
                                intent.putExtra("ORDER_DTO", response.body());
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<OrderDTO> call, Throwable t) {
                                Toast.makeText(ConfirmOrderActivity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ConfirmOrderActivity.this, SearchActivity.class);
                                startActivity(intent);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(ConfirmOrderActivity.this, "Chọn phương thức thanh toán!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void populateUI() {
        tvCustomerName.setText(CUSTOMER.getCustomerName());
        tvCustomerPhone.setText(CUSTOMER.getPhone());
        tvCustomerAddress.setText(CUSTOMER.getAddress());
        ivCustomer.setImageResource(R.drawable.ic_personal_clicked);

    }

    @Override
    protected void onResume() {
        super.onResume();
        populateUI();
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
                    Log.d("Error", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}