package com.example.flowersystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

public class PaymentMethodActivity extends AppCompatActivity {
    Button btnPay;
    RadioButton rbNCB;
    ImageView ivBack;
    ImageView ivOrders;
    ImageView ivHome;
    ImageView ivNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        btnPay = findViewById(R.id.btnPay);
        rbNCB = findViewById(R.id.rbNCB);
        ivBack = findViewById(R.id.ivBack);
        ivOrders = findViewById(R.id.ivOrders);
        ivHome = findViewById(R.id.ivHome);
        ivNotification = findViewById(R.id.ivNotification);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ivOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentMethodActivity.this, OrdersActivity.class);
                startActivity(intent);
            }
        });
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentMethodActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentMethodActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });
    }
}