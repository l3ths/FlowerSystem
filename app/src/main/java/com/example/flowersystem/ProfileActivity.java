package com.example.flowersystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.flowersystem.api.CustomerApi;
import com.example.flowersystem.api.RetrofitClient;
import com.example.flowersystem.dto.CustomerDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileActivity extends AppCompatActivity {
    TextView tvName;
    TextView tvPhone;
    TextView tvEmail;
    TextView tvAddress;
    ImageView ivBack;
    ImageView ivEdit;
    CustomerDTO customerDTO;
    ImageView ivOrders;
    ImageView ivNotification;
    ImageView ivHome;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvName = findViewById(R.id.tvName);
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);
        ivBack = findViewById(R.id.ivBack);
        ivEdit = findViewById(R.id.ivEdit);
        tvAddress = findViewById(R.id.tvAddress);
        ivNotification = findViewById(R.id.ivNotification);
        ivOrders = findViewById(R.id.ivOrders);
        ivHome = findViewById(R.id.ivHome);
        btnLogout = findViewById(R.id.btnLogout);
        getData();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });
        ivOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, OrdersActivity.class);
                startActivity(intent);
            }
        });
        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent != null) {
            long id = Constants.LOGGED_IN_CUSTOMER.getId();
            Retrofit retrofit = RetrofitClient.getInstance();
            CustomerApi customerApi = retrofit.create(CustomerApi.class);
            try {
                Call<CustomerDTO> call = customerApi.getCustomer(id);
                call.enqueue(new Callback<CustomerDTO>() {
                    @Override
                    public void onResponse(Call<CustomerDTO> call, Response<CustomerDTO> response) {
                        customerDTO = response.body();
                        tvName.setText(customerDTO.getCustomerName());
                        tvPhone.setText(customerDTO.getPhone());
                        tvAddress.setText(customerDTO.getAddress());
                        tvEmail.setText(customerDTO.getEmail());
                    }

                    @Override
                    public void onFailure(Call<CustomerDTO> call, Throwable t) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}