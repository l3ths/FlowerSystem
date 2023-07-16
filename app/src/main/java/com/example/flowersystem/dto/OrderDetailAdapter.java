package com.example.flowersystem.dto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flowersystem.ConfirmOrderActivity;
import com.example.flowersystem.R;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>{
    Context context;
    List<OrderDetailDTO> cartList;

    public OrderDetailAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_confirm_order, parent, false);
        return new OrderDetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetailDTO cartDTO = cartList.get(position);
        FlowerDTO flowerDTO = cartDTO.getFlowerDTO();
        holder.tvName.setText(flowerDTO.getFlowerName());
        holder.tvPrice.setText(cartDTO.getUnitPrice() + "");
        holder.tvQuantity.setText(cartDTO.getQuantity() + "");
        Glide.with(context)
                .load(flowerDTO.getImage())
                .centerCrop()
                .into(holder.ivImage);
    }

    public int getItemCount() {
        if (cartList == null)
            return 0;
        return cartList.size();
    }

    public void setTasks(List<OrderDetailDTO> list) {
        cartList = list;
        notifyDataSetChanged();
    }

    public List<OrderDetailDTO> getTasks() {
        return cartList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvPrice;
        ImageView ivImage;
        TextView tvQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvConfirmCartName);
            tvPrice = itemView.findViewById(R.id.tvConfirmCartPrice);
            ivImage = itemView.findViewById(R.id.ivConfirm);
            tvQuantity = itemView.findViewById(R.id.tvConfirmQuantity);

        }
    }
}
