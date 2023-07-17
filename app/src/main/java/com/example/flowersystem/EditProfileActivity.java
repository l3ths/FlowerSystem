package com.example.flowersystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.flowersystem.R;
import com.example.flowersystem.api.CustomerApi;
import com.example.flowersystem.api.RetrofitClient;
import com.example.flowersystem.dto.CustomerDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditProfileActivity extends AppCompatActivity {
    EditText etName;
    EditText etAddress;
    EditText etPhone;
    EditText etEmail;
    ImageView ivBack;
    CustomerDTO customerDTO;
    Button btnSave;
    ImageView ivOrders;
    ImageView ivNotification;
    ImageView ivHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etPhone.setEnabled(false);
        etAddress = findViewById(R.id.etAddress);
        etEmail = findViewById(R.id.etEmail);
        ivBack = findViewById(R.id.ivBack);
        btnSave = findViewById(R.id.btnSave);
        ivNotification = findViewById(R.id.ivNotification);
        ivOrders = findViewById(R.id.ivOrders);
        ivHome = findViewById(R.id.ivHome);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEditTextEmpty(etName) || checkEditTextEmpty(etAddress) || checkEditTextEmpty(etEmail)) {
                    Toast.makeText(EditProfileActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else if (!checkValidEmail(etEmail.getText().toString().trim())) {
                    Toast.makeText(EditProfileActivity.this, "Email không hợp lệ!", Toast.LENGTH_SHORT).show();
                } else {
                    updateProfile();
                    Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
            }
        });
        ivOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, OrdersActivity.class);
                startActivity(intent);
            }
        });
        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateProfile() {
        CustomerDTO updatedCustomer = Constants.LOGGED_IN_CUSTOMER;
        updatedCustomer.setCustomerName(etName.getText().toString());
        updatedCustomer.setAddress(etAddress.getText().toString());
        updatedCustomer.setEmail(etEmail.getText().toString());
        Retrofit retrofit = RetrofitClient.getInstance();
        CustomerApi customerApi = retrofit.create(CustomerApi.class);
        Call<Void> call = customerApi.update(updatedCustomer.getId(), updatedCustomer);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("UPDATE_PROFILE", response.code() + "");
                finish();
                Toast.makeText(EditProfileActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
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
                        etName.setText(customerDTO.getCustomerName());
                        etPhone.setText(customerDTO.getPhone());
                        etAddress.setText(customerDTO.getAddress());
                        etEmail.setText(customerDTO.getEmail());
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

    private boolean checkEditTextEmpty(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }

    private boolean checkValidEmail(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}