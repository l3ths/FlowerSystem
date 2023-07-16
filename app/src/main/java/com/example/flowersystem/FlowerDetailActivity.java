package com.example.flowersystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.flowersystem.api.CartApi;
import com.example.flowersystem.api.FlowerApi;
import com.example.flowersystem.api.RetrofitClient;
import com.example.flowersystem.dto.CartDTO;
import com.example.flowersystem.dto.CustomerDTO;
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
    TextView tvAddToCart;
    int quantity;
    int maxQuantity;
    FlowerDTO flowerDTO;
    CustomerDTO CUSTOMER = Constants.LOGGED_IN_CUSTOMER;


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
        tvAddToCart = findViewById(R.id.tvAddToCart);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = RetrofitClient.getInstance();
                CartApi cartApi = retrofit.create(CartApi.class);
                Call<CartDTO> call = cartApi.deleteAllCart(CUSTOMER.getId());
                call.enqueue(new Callback<CartDTO>() {
                    @Override
                    public void onResponse(Call<CartDTO> call, Response<CartDTO> response) {
                    }

                    @Override
                    public void onFailure(Call<CartDTO> call, Throwable t) {
                    }
                });
                FlowerDTO flower = new FlowerDTO(flowerDTO.getId());
                CartDTO cart = new CartDTO(flower, quantity);
                call = cartApi.addToCart(CUSTOMER.getId(),cart);
                call.enqueue(new Callback<CartDTO>() {
                    @Override
                    public void onResponse(Call<CartDTO> call, Response<CartDTO> response) {
                    }

                    @Override
                    public void onFailure(Call<CartDTO> call, Throwable t) {
                        Intent intent = new Intent(FlowerDetailActivity.this, ConfirmOrderActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

        llAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = RetrofitClient.getInstance();
                CartApi cartApi = retrofit.create(CartApi.class);
                FlowerDTO flower = new FlowerDTO(flowerDTO.getId());
                CartDTO cart = new CartDTO(flower, quantity);
                try {
                    Call<CartDTO> call = cartApi.addToCart(CUSTOMER.getId(), cart);
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            Toast.makeText(FlowerDetailActivity.this, "Đã thêm vào giỏ hàng!"+response.code(), Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(FlowerDetailActivity.this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(FlowerDetailActivity.this,SearchActivity.class);
                            startActivity(intent);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlowerDetailActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        quantity = Integer.parseInt(tvQuantity.getText().toString());
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity < maxQuantity) {
                    quantity++;
                    tvQuantity.setText(quantity + "");
                } else {
                    Toast.makeText(FlowerDetailActivity.this, "Đã đạt tối đa số lượng!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity--;
                    tvQuantity.setText(quantity + "");
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    public void getData() {
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
                        flowerDTO = response.body();
                        tvName.setText(flowerDTO.getFlowerName());
                        tvPrice.setText(flowerDTO.getUnitPrice() + "");
                        tvDetails.setText(flowerDTO.getFlowerDescription());
                        Glide.with(FlowerDetailActivity.this)
                                .load(flowerDTO.getImage())
                                .centerCrop()
                                .into(ivImage);
                        maxQuantity = flowerDTO.getStock();
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