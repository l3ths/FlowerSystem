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
import java.util.List;

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
            Call<List<FlowerDTO>> call = flowerApi.getAllFlowers();
            call.enqueue(new Callback<List<FlowerDTO>>() {
                @Override
                public void onResponse(Call<List<FlowerDTO>> call, Response<List<FlowerDTO>> response) {
                    List<FlowerDTO> list = response.body();
                    ArrayList<Flower> flowerList = new ArrayList<>();
                    Flower flower = new Flower(list.get(0).getId(),list.get(0).getFlowerName(),list.get(0).getFlowerDescription(),list.get(0).getImage(),list.get(0).getUnitPrice());
                    flowerList.add(flower);
                    adapter.setTasks(flowerList);
                }

                @Override
                public void onFailure(Call<List<FlowerDTO>> call, Throwable t) {

                }
            });

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}