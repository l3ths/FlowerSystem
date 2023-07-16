package com.example.flowersystem.dto;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowersystem.ConfirmOrderActivity;
import com.example.flowersystem.Constants;
import com.example.flowersystem.OrdersActivity;
import com.example.flowersystem.R;

import java.util.List;

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
        if (TextUtils.equals(orderFlower.getOrderDTO().getPaymentMethod(),"COD")) {
            holder.tvMethod.setText("Thanh toán khi nhận hàng");
        } else {
            holder.tvMethod.setText("Thanh toán trực tuyến");
        }
        OrderDetailAdapter adapter = new OrderDetailAdapter(context);
        adapter.setTasks(orderFlower.getList());

        holder.rvFlower.setLayoutManager(new LinearLayoutManager(context));
        holder.rvFlower.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        if (orderFlowerList==null)
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvOrderID);
            tvAddress = itemView.findViewById(R.id.tvOrderAddress);
            tvTotal = itemView.findViewById(R.id.tvTotalOrderPrice);
            tvMethod = itemView.findViewById(R.id.tvOrderMethod);
            rvFlower = itemView.findViewById(R.id.rvOrderFlower);
        }
    }
}
