package com.example.flowersystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    ImageView ivBack;
    EditText etEmail;
    EditText etPassword;
    TextView tvForgotPassword;
    TextView tvRegister;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ivBack = findViewById(R.id.ivBack);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvForgotPassword = findViewById(R.id.tvForgotPasswordRedirect);
        tvRegister = findViewById(R.id.tvRegisterRedirect);
        btnLogin = findViewById(R.id.btnLogin);

    }
}