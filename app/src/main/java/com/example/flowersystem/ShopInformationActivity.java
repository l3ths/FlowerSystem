package com.example.flowersystem;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.flowersystem.api.RetrofitClient;
import com.example.flowersystem.api.ShopProfileApi;
import com.example.flowersystem.dto.ShopProfileDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShopInformationActivity extends AppCompatActivity {
    private ImageView ivLogo;
    private TextView tvShopName;
    private TextView tvShopPhone;
    private TextView tvShopAddress;
    ImageView ivBack;
    private RelativeLayout relativeLayoutLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_information);
        ivLogo = findViewById(R.id.ivLogo);
        tvShopName = findViewById(R.id.tvShopName);
        tvShopPhone = findViewById(R.id.tvShopPhone);
        tvShopAddress = findViewById(R.id.tvShopAddress);
        relativeLayoutLoading = findViewById(R.id.loadingPanel);
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadScreenData();
    }

    private void loadScreenData() {
        Retrofit retrofit = RetrofitClient.getInstance();
        ShopProfileApi shopProfileApi = retrofit.create(ShopProfileApi.class);
        Call<ShopProfileDTO> call = shopProfileApi.getShopProfile();
        startLoadingAnimation();
        call.enqueue(new Callback<ShopProfileDTO>() {
            @Override
            public void onResponse(Call<ShopProfileDTO> call, Response<ShopProfileDTO> response) {
                finishLoadingAnimation();
                if (response.isSuccessful()) {
                    ShopProfileDTO shopProfileDTO = response.body();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvShopName.setText(shopProfileDTO.getName());
                            tvShopAddress.setText(shopProfileDTO.getAddress());
                            tvShopPhone.setText(shopProfileDTO.getPhone());
//                           Load Logo
                            Glide.with(ShopInformationActivity.this)
                                    .load(shopProfileDTO.getLogo()) //image link
                                    .centerCrop()
                                    .into(ivLogo);
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ShopInformationActivity.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ShopProfileDTO> call, Throwable t) {
                finishLoadingAnimation();
                Log.e("ShopProfile_ERROR", t.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ShopInformationActivity.this, "Lỗi hệ thống, kiểm tra kết nối internet", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

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