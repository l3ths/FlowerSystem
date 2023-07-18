package com.example.flowersystem.dto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flowersystem.FlowerDetailActivity;
import com.example.flowersystem.R;
import com.example.flowersystem.SearchActivity;
import com.example.flowersystem.ShopInformationActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class SearchFlowerAdapter extends RecyclerView.Adapter<SearchFlowerAdapter.ViewHolder>{
    SearchActivity context;
    List<FlowerDTO> flowerList;

    public SearchFlowerAdapter(SearchActivity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public SearchFlowerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchFlowerAdapter.ViewHolder holder, int position) {
        FlowerDTO flower = flowerList.get(position);
        holder.tvSearchName.setText(flower.getFlowerName());
        holder.tvSearchPrice.setText(flower.getUnitPrice()+" VND");
        Glide.with(context)
                .load(flower.getImage())
                .centerCrop()
                .into(holder.ivSearchImage);
        holder.clSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.goToDetail(flower.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (flowerList==null) return 0;
        return flowerList.size();
    }

    public void setTasks(List<FlowerDTO> list) {
        flowerList = list;
        notifyDataSetChanged();
    }

    public List<FlowerDTO> getTasks() {
        return flowerList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSearchName;
        TextView tvSearchPrice;
        ImageView ivSearchImage;
        CardView clSearch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSearchName = itemView.findViewById(R.id.tvSearchName);
            tvSearchPrice = itemView.findViewById(R.id.tvSearchPrice);
            ivSearchImage = itemView.findViewById(R.id.ivSearchImage);
            clSearch = itemView.findViewById(R.id.clSearch);

        }
    }
}

