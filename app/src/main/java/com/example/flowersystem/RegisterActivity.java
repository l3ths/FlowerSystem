package com.example.flowersystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.flowersystem.api.ConnectionConfig;
import com.example.flowersystem.api.CustomerApi;
import com.example.flowersystem.api.JwtTokenManager;
import com.example.flowersystem.api.LoginApi;
import com.example.flowersystem.api.RetrofitClient;
import com.example.flowersystem.dto.CustomerDTO;
import com.example.flowersystem.dto.JwtTokenDTO;
import com.example.flowersystem.dto.LoginInforDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private ImageView ivBack;
    private EditText etName;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;
    private EditText etAddress;
    private final String REQUIRED_FIELD = "Bắt buộc";
    private final String PHONE_HAS_BEEN_USED = "Số điện thoại đã được sử dụng";
    private final String PASSWORD_MUST_MATCH = "Xác nhận mật khẩu không đúng";
    private RelativeLayout relativeLayoutLoading;
    private final int HTTP_DUPLICATED_CODE = 409;
    private final int HTTP_CREATED_CODE = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ivBack = findViewById(R.id.ivBack);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etAddress = findViewById(R.id.etAddress);
        btnRegister = findViewById(R.id.btnRegister);
        relativeLayoutLoading = findViewById(R.id.loadingPanel);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAction();
            }
        });

    }

    private void registerAction() {
        etName.clearFocus();
        etPhone.clearFocus();
        etAddress.clearFocus();
        etPassword.clearFocus();
        etConfirmPassword.clearFocus();
        etEmail.clearFocus();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            etPhone.setError(REQUIRED_FIELD);
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setError(REQUIRED_FIELD);
        } else if (TextUtils.isEmpty(confirmPassword)) {
            etConfirmPassword.setError(REQUIRED_FIELD);
        } else if (!TextUtils.equals(password, confirmPassword)) {
            etConfirmPassword.setError(PASSWORD_MUST_MATCH);
        } else {
            String name = etName.getText().toString();
            String address = etAddress.getText().toString();
            String email = etEmail.getText().toString();
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setCustomerName(name);
            customerDTO.setAddress(address);
            customerDTO.setEmail(email);
            customerDTO.setPhone(phone);
            customerDTO.setPassword(password);
            Retrofit retrofit = new Retrofit.Builder().baseUrl(ConnectionConfig.BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
            CustomerApi customerApi = retrofit.create(CustomerApi.class);
            Call<Void> call = customerApi.register(customerDTO);
            startLoadingAnimation();
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            finishLoadingAnimation();
                        }
                    });
                    if (response.code() == HTTP_DUPLICATED_CODE) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                etPhone.setError(PHONE_HAS_BEEN_USED);
                            }
                        });
                    } else if (response.code() == HTTP_CREATED_CODE) {

                        login(customerDTO);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            finishLoadingAnimation();
                        }
                    });
                    Log.e("REGISTER_ERROR", t.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, "Lỗi hệ thống", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });

        }
    }

    private void startLoadingAnimation() {
        relativeLayoutLoading.setVisibility(View.VISIBLE);
        relativeLayoutLoading.setClickable(true);
    }

    private void finishLoadingAnimation() {
        relativeLayoutLoading.setVisibility(View.GONE);
        relativeLayoutLoading.setClickable(false);
    }

    private void login(CustomerDTO customerDTO) {
        Retrofit instance = new Retrofit.Builder().baseUrl(ConnectionConfig.BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        LoginApi loginApi = instance.create(LoginApi.class);
        LoginInforDTO loginInforDTO = new LoginInforDTO();
        loginInforDTO.setPassword(customerDTO.getPassword());
        loginInforDTO.setPhone(customerDTO.getPhone());
        Call<JwtTokenDTO> call = loginApi.login(loginInforDTO);

        startLoadingAnimation();
        call.enqueue(new Callback<JwtTokenDTO>() {
            @Override
            public void onResponse(Call<JwtTokenDTO> call, Response<JwtTokenDTO> response) {
                finishLoadingAnimation();

                if (response.isSuccessful()) {
                    JwtTokenDTO jwtTokenDTO = response.body();
                    if (jwtTokenDTO.getToken() == null || jwtTokenDTO.getToken().isEmpty()) {
                    } else {
                        JwtTokenManager.JWT_TOKEN = jwtTokenDTO.getToken();
                        Intent intent = new Intent(RegisterActivity.this, SearchActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }

            @Override
            public void onFailure(Call<JwtTokenDTO> call, Throwable t) {
                finishLoadingAnimation();
                Log.e("LOGIN_ERROR", t.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity.this, "Lỗi hệ thống", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}