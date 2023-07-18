package com.example.flowersystem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flowersystem.api.ConnectionConfig;
import com.example.flowersystem.api.CustomerApi;
import com.example.flowersystem.api.JwtTokenManager;
import com.example.flowersystem.api.LoginApi;
import com.example.flowersystem.api.RetrofitClient;
import com.example.flowersystem.dto.FirebaseNotiTokenDTO;
import com.example.flowersystem.dto.JwtTokenDTO;
import com.example.flowersystem.dto.LoginInforDTO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

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
                if (tvLoginFailed.getVisibility() == View.VISIBLE) {
                    tvLoginFailed.setVisibility(View.GONE);
                }
            }
        });
        etPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (tvLoginFailed.getVisibility() == View.VISIBLE) {
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
        askNotificationPermission();
    }

    private void sendFirebaseNotificationToServer() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FETCH FCM", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.d("Firebase msg token:", token);
                        Retrofit retrofit = RetrofitClient.getInstance();
                        CustomerApi customerApi = retrofit.create(CustomerApi.class);
                        FirebaseNotiTokenDTO firebaseNotiTokenDTO = new FirebaseNotiTokenDTO();
                        firebaseNotiTokenDTO.setToken(token);
                        Call<Void> call = customerApi.updateCustomerNotificationToken(Constants.LOGGED_IN_CUSTOMER.getId(), firebaseNotiTokenDTO);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Log.d("SEND_NOTI_TOKEN", response.code() + "");
                                finish();

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                t.printStackTrace();
                                finish();
                            }
                        });

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
                            JwtTokenManager.JWT_TOKEN = "Bearer " + jwtTokenDTO.getToken();
                            Constants.LOGGED_IN_CUSTOMER = jwtTokenDTO.getCustomer();
                            Intent intent;
                            if (Constants.LOGGED_IN_CUSTOMER.getRole() != null) {
                                intent = new Intent(LoginActivity.this, OrderManagerActivity.class);
                            } else {
                                intent = new Intent(LoginActivity.this, SearchActivity.class);
                            }
                            startActivity(intent);
                            sendFirebaseNotificationToServer();
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

    // [START ask_post_notifications]
    // Declare the launcher at the top of your Activity/Fragment:
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });

    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }
    // [END ask_post_notifications]


}