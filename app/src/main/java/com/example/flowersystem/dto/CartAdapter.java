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
import com.example.flowersystem.R;
import com.example.flowersystem.SearchActivity;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    CartActivity context;
    List<CartDTO> cartList;

    public CartAdapter(CartActivity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartDTO cartDTO = cartList.get(position);
        FlowerDTO flowerDTO = cartDTO.getFlowerDTO();
        holder.tvCartName.setText(flowerDTO.getFlowerName());
        holder.tvCartPrice.setText(flowerDTO.getUnitPrice() + "");
        holder.tvQuantity.setText(cartDTO.getQuantity()+"");
        holder.tvCartDetail.setText(flowerDTO.getFlowerDescription());
        Glide.with(context)
                .load(flowerDTO.getImage())
                .centerCrop()
                .into(holder.ivCartImage);
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
        TextView tvCartName;
        TextView tvCartPrice;
        ImageView ivCartImage;
        TextView tvCartDetail;
        ConstraintLayout clCart;
        ImageView ivPlus;
        ImageView ivMinus;
        TextView tvQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCartName = itemView.findViewById(R.id.tvCartFlowerName);
            tvCartPrice = itemView.findViewById(R.id.tvCartFlowerPrice);
            ivCartImage = itemView.findViewById(R.id.ivCartFlowerImage);
            clCart = itemView.findViewById(R.id.clCart);
            ivPlus = itemView.findViewById(R.id.ivCartAdd);
            ivMinus = itemView.findViewById(R.id.ivCartMinus);
            tvQuantity = itemView.findViewById(R.id.tvCartQuantity);
            tvCartDetail = itemView.findViewById(R.id.tvCartFlowerDetails);

        }
    }
}
