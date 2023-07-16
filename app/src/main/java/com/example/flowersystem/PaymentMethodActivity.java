package com.example.flowersystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;

public class PaymentMethodActivity extends AppCompatActivity {
    Button btnPay;
    RadioButton rbNCB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        btnPay = findViewById(R.id.btnPay);
        rbNCB = findViewById(R.id.rbNCB);
    }
}