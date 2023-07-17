package com.example.flowersystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.flowersystem.api.FlowerApi;
import com.example.flowersystem.api.RetrofitClient;
import com.example.flowersystem.dto.Flower;
import com.example.flowersystem.dto.FlowerDTO;
import com.example.flowersystem.ProfileActivity;
import com.example.flowersystem.dto.SearchFlowerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity {
    RecyclerView rvSearch;
    SearchFlowerAdapter adapter;
    ImageView ivCart;
    ImageView ivOrders;
    ImageView ivNotification;
    ImageView ivPersonal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        rvSearch = findViewById(R.id.rvSearch);
        ivCart = findViewById(R.id.ivCart);
        ivOrders = findViewById(R.id.ivOrders);
        ivNotification = findViewById(R.id.ivNotification);
        ivPersonal = findViewById(R.id.ivPersonal);

        ivOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, OrdersActivity.class);
                startActivity(intent);
            }
        });
        ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        ivPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchActivity.this,2);
        rvSearch.setLayoutManager(new GridLayoutManager(SearchActivity.this,2));
        adapter = new SearchFlowerAdapter(SearchActivity.this);
        rvSearch.setAdapter(adapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        retrieveTask();
    }
    private void retrieveTask(){
        Retrofit retrofit = RetrofitClient.getInstance();
        FlowerApi flowerApi = retrofit.create(FlowerApi.class);
        try {
            Call<List<FlowerDTO>> call = flowerApi.getAllFlowers();
            call.enqueue(new Callback<List<FlowerDTO>>() {
                @Override
                public void onResponse(Call<List<FlowerDTO>> call, Response<List<FlowerDTO>> response) {
                    List<FlowerDTO> list = response.body();
                    adapter.setTasks(list);
                }

                @Override
                public void onFailure(Call<List<FlowerDTO>> call, Throwable t) {

                }
            });

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void goToDetail(Long id){
        Intent intent = new Intent(SearchActivity.this, FlowerDetailActivity.class);
        intent.putExtra("flowerDetailID",id);
        startActivity(intent);
    }
}