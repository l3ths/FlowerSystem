package com.example.flowersystem.dto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowersystem.Constants;
import com.example.flowersystem.OrdersActivity;
import com.example.flowersystem.R;
import com.example.flowersystem.api.CartApi;
import com.example.flowersystem.api.OrderApi;
import com.example.flowersystem.api.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    OrdersActivity context;
    List<OrderFlower> orderFlowerList;

    public OrderAdapter(OrdersActivity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderFlower orderFlower = orderFlowerList.get(position);
        holder.tvId.setText(orderFlower.getOrderDTO().getId() + "");
        holder.tvAddress.setText(Constants.LOGGED_IN_CUSTOMER.getAddress());
        holder.tvTotal.setText(orderFlower.getOrderDTO().getTotal() + "");
        if (!TextUtils.equals(orderFlower.getOrderDTO().getOrderStatus(),Constants.OrderStatusNumber.CREATED))
            holder.ivCancel.setVisibility(View.INVISIBLE);
        else holder.ivCancel.setVisibility(View.VISIBLE);
        if (TextUtils.equals(orderFlower.getOrderDTO().getPaymentMethod(), "COD")) {
            holder.tvMethod.setText("Thanh toán khi nhận hàng");
        } else {
            holder.tvMethod.setText("Thanh toán trực tuyến");
        }
        OrderDetailAdapter adapter = new OrderDetailAdapter(context);
        adapter.setTasks(orderFlower.getList());
        holder.rvFlower.setLayoutManager(new LinearLayoutManager(context));
        holder.rvFlower.setAdapter(adapter);
        holder.ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.cancelOrder(orderFlower);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (orderFlowerList == null)
            return 0;
        return orderFlowerList.size();
    }

    public void setTasks(List<OrderFlower> list) {
        orderFlowerList = list;
        notifyDataSetChanged();
    }

    public List<OrderFlower> getTasks() {
        return orderFlowerList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId;
        TextView tvAddress;
        TextView tvTotal;
        TextView tvMethod;
        RecyclerView rvFlower;
        ImageView ivCancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvOrderID);
            tvAddress = itemView.findViewById(R.id.tvOrderAddress);
            tvTotal = itemView.findViewById(R.id.tvTotalOrderPrice);
            tvMethod = itemView.findViewById(R.id.tvOrderMethod);
            rvFlower = itemView.findViewById(R.id.rvOrderFlower);
            ivCancel = itemView.findViewById(R.id.ivCancel);
        }
    }
}
