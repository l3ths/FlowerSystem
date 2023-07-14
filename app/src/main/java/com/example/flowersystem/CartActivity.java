package com.example.flowersystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.flowersystem.api.CartApi;
import com.example.flowersystem.api.FlowerApi;
import com.example.flowersystem.api.RetrofitClient;
import com.example.flowersystem.dto.CartAdapter;
import com.example.flowersystem.dto.CartDTO;
import com.example.flowersystem.dto.FlowerDTO;
import com.example.flowersystem.dto.SearchFlowerAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CartActivity extends AppCompatActivity {
    RecyclerView rvCart;
    TextView tvTotal;
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        rvCart = findViewById(R.id.rvCart);

        rvCart.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        adapter = new CartAdapter(CartActivity.this);
        rvCart.setAdapter(adapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        retrieveTask();
    }
    private void retrieveTask(){
        Retrofit retrofit = RetrofitClient.getInstance();
        CartApi cartApi = retrofit.create(CartApi.class);
        try {
            Call<List<CartDTO>> call = cartApi.getAllCartByID(1L);
            call.enqueue(new Callback<List<CartDTO>>() {
                @Override
                public void onResponse(Call<List<CartDTO>> call, Response<List<CartDTO>> response) {
                    List<CartDTO> list = response.body();
                    adapter.setTasks(list);
                }

                @Override
                public void onFailure(Call<List<CartDTO>> call, Throwable t) {

                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}