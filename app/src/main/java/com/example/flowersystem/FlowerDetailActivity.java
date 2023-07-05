package com.example.flowersystem;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FlowerDetailActivity extends AppCompatActivity {
    ImageView ivBack;
    ImageView ivHelp;
    ImageView ivImage;
    TextView tvName;
    TextView tvPrice;
    TextView tvDetails;
    ImageView ivMinus;
    TextView tvQuantity;
    ImageView ivAdd;
    LinearLayout llAddToCart;
    TextView tvBuyNow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_detail);
        ivBack = findViewById(R.id.ivBack);
        ivHelp = findViewById(R.id.ivHelp);
        ivImage = findViewById(R.id.ivDetailFlowerImage);
        tvName = findViewById(R.id.tvDetailFlowerName);
        tvPrice = findViewById(R.id.tvDetailFlowerPrice);
        tvDetails = findViewById(R.id.tvDetailFlowerDetails);
        ivMinus = findViewById(R.id.ivMinus);
        tvQuantity = findViewById(R.id.tvQuantity);
        ivAdd = findViewById(R.id.ivAdd);
        llAddToCart = findViewById(R.id.llAddToCart);
        tvBuyNow = findViewById(R.id.tvBuyNow);

    }
}