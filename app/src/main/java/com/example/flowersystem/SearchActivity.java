package com.example.flowersystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.flowersystem.api.FlowerApi;
import com.example.flowersystem.api.RetrofitClient;
import com.example.flowersystem.dto.Flower;
import com.example.flowersystem.dto.FlowerDTO;
import com.example.flowersystem.dto.SearchFlowerAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity {
    RecyclerView rvSearch;
    SearchFlowerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        rvSearch = findViewById(R.id.rvSearch);

        rvSearch.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
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
            Call<FlowerDTO[]> call = flowerApi.getAllFlowers();
            call.enqueue(new Callback<FlowerDTO[]>() {
                @Override
                public void onResponse(Call<FlowerDTO[]> call, Response<FlowerDTO[]> response) {
                    FlowerDTO[] flowerDTOS = response.body();
                    ArrayList<Flower> flowerList = new ArrayList<>();
                    for (FlowerDTO flowerDTO:
                         flowerDTOS) {
                        Flower flower = new Flower(flowerDTO.getId(),flowerDTO.getFlowerName(),flowerDTO.getFlowerDescription(),flowerDTO.getImage(),flowerDTO.getUnitPrice());
                        flowerList.add(flower);
                    }
                    adapter.setTasks(flowerList);
                }

                @Override
                public void onFailure(Call<FlowerDTO[]> call, Throwable t) {

                }
            });

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}