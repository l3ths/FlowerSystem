package com.example.flowersystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.flowersystem.dto.CartDTO;

import java.util.List;

public class ConfirmOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        Intent intent = getIntent();
        CartDTO list  = (CartDTO) intent.getSerializableExtra("listCart");
    }
}