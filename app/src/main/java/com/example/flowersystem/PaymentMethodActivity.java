package com.example.flowersystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.flowersystem.dto.OrderDTO;

public class PaymentMethodActivity extends AppCompatActivity {
    Button btnPay;
    RadioButton rbNCB;
    OrderDTO orderDTO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        btnPay = findViewById(R.id.btnPay);
        rbNCB = findViewById(R.id.rbNCB);
        orderDTO = (OrderDTO) getIntent().getSerializableExtra("ORDER_DTO");
        if (orderDTO != null) {
            btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    paymentAction(orderDTO);
                }
            });
        }
    }

    private void paymentAction(OrderDTO orderDTO) {
        Intent intent =new Intent(this, PrepaidActivity.class);
        intent.putExtra("ORDER_DTO", orderDTO);
        startActivity(intent);
    }
}