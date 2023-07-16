package com.example.flowersystem.dto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flowersystem.CartActivity;
import com.example.flowersystem.R;
import com.example.flowersystem.SearchActivity;
import com.example.flowersystem.api.CartApi;
import com.example.flowersystem.api.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
        holder.tvQuantity.setText(cartDTO.getQuantity() + "");
        holder.tvCartDetail.setText(flowerDTO.getFlowerDescription());
        Glide.with(context)
                .load(flowerDTO.getImage())
                .centerCrop()
                .into(holder.ivCartImage);
        holder.ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Xoá khỏi giỏ hàng.")
                        .setMessage("Xoá " + flowerDTO.getFlowerName() + " khỏi giỏ hàng?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("XOÁ", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Retrofit retrofit = RetrofitClient.getInstance();
                                CartApi cartApi = retrofit.create(CartApi.class);
                                Call call = cartApi.deleteCart(cartDTO.getId());
                                call.enqueue(new Callback() {
                                    @Override
                                    public void onResponse(Call call, Response response) {
                                        Toast.makeText(context, "Xoá thành công!", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onFailure(Call call, Throwable t) {
                                        cartList.remove(cartDTO);
                                        Toast.makeText(context, "Xoá thành công!!", Toast.LENGTH_LONG).show();
                                        notifyDataSetChanged();
                                        context.calculateTotal();
                                    }
                                });
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("HUỶ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }

        });
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
        TextView tvQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCartName = itemView.findViewById(R.id.tvCartFlowerName);
            tvCartPrice = itemView.findViewById(R.id.tvCartFlowerPrice);
            ivCartImage = itemView.findViewById(R.id.ivCartFlowerImage);
            clCart = itemView.findViewById(R.id.clCart);
            ivPlus = itemView.findViewById(R.id.ivCartAdd);
            tvQuantity = itemView.findViewById(R.id.tvCartQuantity);
            tvCartDetail = itemView.findViewById(R.id.tvCartFlowerDetails);

        }
    }
}
