package com.example.flowersystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.flowersystem.api.ConnectionConfig;
import com.example.flowersystem.api.JwtTokenManager;
import com.example.flowersystem.api.LoginApi;
import com.example.flowersystem.api.OrderApi;
import com.example.flowersystem.api.RetrofitClient;
import com.example.flowersystem.dto.JwtTokenDTO;
import com.example.flowersystem.dto.LoginInforDTO;
import com.example.flowersystem.dto.OrderDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class TestApiToken extends AppCompatActivity {
    private EditText edtPhone;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView tvToken;
    private Button btnCheckGetOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_api_token);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvToken = findViewById(R.id.tvToken);
        btnCheckGetOrder = findViewById(R.id.btnCheckGetOrder);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofitClient = new Retrofit.Builder().baseUrl(ConnectionConfig.BASE_URL)
                        .addConverterFactory(JacksonConverterFactory.create())
                        .build();
                LoginInforDTO loginInforDTO = new LoginInforDTO();
                loginInforDTO.setPhone(edtPhone.getText().toString());
                loginInforDTO.setPassword(edtPassword.getText().toString());
                try {
                    LoginApi loginApi = retrofitClient.create(LoginApi.class);
                    Call<JwtTokenDTO> call = loginApi.login(loginInforDTO);
                    call.enqueue(new Callback<JwtTokenDTO>() {
                        @Override
                        public void onResponse(Call<JwtTokenDTO> call, Response<JwtTokenDTO> response) {
                            if (response.isSuccessful()) {
                                JwtTokenManager.JWT_TOKEN = "Bearer " + response.body().getToken();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tvToken.setText(response.body().getToken());
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<JwtTokenDTO> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        btnCheckGetOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = RetrofitClient.getInstance();
                OrderApi orderApi = retrofit.create(OrderApi.class);
                try {
                    Call<OrderDTO> orderDTOCall = orderApi.getOrderById(1l);
                    orderDTOCall.enqueue(new Callback<OrderDTO>() {
                        @Override
                        public void onResponse(Call<OrderDTO> call, Response<OrderDTO> response) {
                            if (response.isSuccessful()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        OrderDTO orderDTO = response.body();
                                        Log.d("OK OK", "order id: " + orderDTO.getId() + ", order status: " + orderDTO.getOrderStatus());
                                        tvToken.setText("order id: " + orderDTO.getId() + ", order status: " + orderDTO.getOrderStatus());
                                    }

                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<OrderDTO> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
}