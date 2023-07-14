package com.example.flowersystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flowersystem.api.FlowerApi;
import com.example.flowersystem.api.RetrofitClient;
import com.example.flowersystem.dto.Flower;
import com.example.flowersystem.dto.FlowerDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FlowerDetailActivity extends AppCompatActivity {
    ImageView ivBack;
    ImageView ivCart;
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
        ivCart = findViewById(R.id.ivCart);
        ivImage = findViewById(R.id.ivDetailFlowerImage);
        tvName = findViewById(R.id.tvDetailFlowerName);
        tvPrice = findViewById(R.id.tvDetailFlowerPrice);
        tvDetails = findViewById(R.id.tvDetailFlowerDetails);
        ivMinus = findViewById(R.id.ivMinus);
        tvQuantity = findViewById(R.id.tvQuantity);
        ivAdd = findViewById(R.id.ivAdd);
        llAddToCart = findViewById(R.id.llAddToCart);
        tvBuyNow = findViewById(R.id.tvBuyNow);

        Intent intent = getIntent();
        if (intent != null) {
            long id = intent.getLongExtra("flowerDetailID", 113);
            Retrofit retrofit = RetrofitClient.getInstance();
            FlowerApi flowerApi = retrofit.create(FlowerApi.class);
            try {
                Call<FlowerDTO> call = flowerApi.getFlower(id);
                call.enqueue(new Callback<FlowerDTO>() {
                    @Override
                    public void onResponse(Call<FlowerDTO> call, Response<FlowerDTO> response) {
                        tvName.setText(response.body().getFlowerName());
                        tvPrice.setText(response.body().getUnitPrice() + "");
                        tvDetails.setText(response.body().getFlowerDescription());
                        Glide.with(FlowerDetailActivity.this)
                                .load(response.body().getImage())
                                .centerCrop()
                                .into(ivImage);
                    }

                    @Override
                    public void onFailure(Call<FlowerDTO> call, Throwable t) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}