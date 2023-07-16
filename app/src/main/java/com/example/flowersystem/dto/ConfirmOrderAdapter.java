package com.example.flowersystem.dto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flowersystem.CartActivity;
import com.example.flowersystem.ConfirmOrderActivity;
import com.example.flowersystem.R;

import java.util.List;

public class ConfirmOrderAdapter extends RecyclerView.Adapter<ConfirmOrderAdapter.ViewHolder>{
    ConfirmOrderActivity context;
    List<CartDTO> cartList;

    public ConfirmOrderAdapter(ConfirmOrderActivity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_confirm_order, parent, false);
        return new ConfirmOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartDTO cartDTO = cartList.get(position);
        FlowerDTO flowerDTO = cartDTO.getFlowerDTO();
        holder.tvName.setText(flowerDTO.getFlowerName());
        holder.tvPrice.setText(flowerDTO.getUnitPrice() + "");
        holder.tvQuantity.setText(cartDTO.getQuantity() + "");
        Glide.with(context)
                .load(flowerDTO.getImage())
                .centerCrop()
                .into(holder.ivImage);

    }

    @Override
    public int getItemCount() {
        if (cartList == null)
            return 0;
        return cartList.size();
    }

    public void setTasks(List<CartDTO> list) {
        cartList = list;
        notifyDataSetChanged();
    }

    public List<CartDTO> getTasks() {
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
