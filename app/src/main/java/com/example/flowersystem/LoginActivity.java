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
import android.widget.TextView;
import android.widget.Toast;

import com.example.flowersystem.api.ConnectionConfig;
import com.example.flowersystem.api.JwtTokenManager;
import com.example.flowersystem.api.LoginApi;
import com.example.flowersystem.dto.JwtTokenDTO;
import com.example.flowersystem.dto.LoginInforDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText etPhone;
    private EditText etPassword;
    private TextView tvForgotPassword;
    private TextView tvRegister;
    private Button btnLogin;
    private TextView tvLoginFailed;
    private RelativeLayout relativeLayoutLoading;
    private final String REQUIRED_FIELD = "Bắt buộc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        tvForgotPassword = findViewById(R.id.tvForgotPasswordRedirect);
        tvRegister = findViewById(R.id.tvRegisterRedirect);
        btnLogin = findViewById(R.id.btnLogin);
        tvLoginFailed = findViewById(R.id.tvLoginFailed);
        relativeLayoutLoading = findViewById(R.id.loadingPanel);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPhone.clearFocus();
                etPassword.clearFocus();
                loginAction();
            }
        });

        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(tvLoginFailed.getVisibility() == View.VISIBLE){
                    tvLoginFailed.setVisibility(View.GONE);
                }
            }
        });
        etPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(tvLoginFailed.getVisibility() == View.VISIBLE){
                    tvLoginFailed.setVisibility(View.GONE);
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    private void loginAction() {
        String phone = etPhone.getText().toString();
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            etPhone.setError(REQUIRED_FIELD);
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setError(REQUIRED_FIELD);
        } else {
            Retrofit instance = new Retrofit.Builder().baseUrl(ConnectionConfig.BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
            LoginApi loginApi = instance.create(LoginApi.class);
            LoginInforDTO loginInforDTO = new LoginInforDTO();
            loginInforDTO.setPassword(password);
            loginInforDTO.setPhone(phone);
            Call<JwtTokenDTO> call = loginApi.login(loginInforDTO);

            startLoadingAnimation();
            call.enqueue(new Callback<JwtTokenDTO>() {
                @Override
                public void onResponse(Call<JwtTokenDTO> call, Response<JwtTokenDTO> response) {
                    finishLoadingAnimation();

                    if (response.isSuccessful()) {
                        JwtTokenDTO jwtTokenDTO = response.body();
                        if (jwtTokenDTO.getToken() == null || jwtTokenDTO.getToken().isEmpty()) {
                            tvLoginFailed.setVisibility(View.VISIBLE);
                        } else {
                            JwtTokenManager.JWT_TOKEN = jwtTokenDTO.getToken();
                            Intent intent = new Intent(LoginActivity.this, SearchActivity.class);
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
                            Toast.makeText(LoginActivity.this, "Lỗi hệ thống", Toast.LENGTH_LONG).show();
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
}