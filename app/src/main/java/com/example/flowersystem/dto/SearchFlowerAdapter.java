package com.example.flowersystem.dto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowersystem.R;
import com.example.flowersystem.SearchActivity;

import java.util.List;

public class SearchFlowerAdapter extends RecyclerView.Adapter<SearchFlowerAdapter.ViewHolder>{
    SearchActivity context;
    List<Flower> flowerList;

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
        Flower flower = flowerList.get(position);
        holder.tvSearchName.setText(flower.getFlowerName());
        holder.tvSearchPrice.setText(flower.getUnitPrice()+"");
        holder.ivSearchImage.setImageResource(R.drawable.ic_cart);
    }

    @Override
    public int getItemCount() {
        if (flowerList==null) return 0;
        return flowerList.size();
    }

    public void setTasks(List<Flower> list) {
        flowerList = list;
        notifyDataSetChanged();
    }

    public List<Flower> getTasks() {
        return flowerList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSearchName;
        TextView tvSearchPrice;
        ImageView ivSearchImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSearchName = itemView.findViewById(R.id.tvSearchName);
            tvSearchPrice = itemView.findViewById(R.id.tvSearchPrice);
            ivSearchImage = itemView.findViewById(R.id.ivSearchImage);

        }
    }
}

