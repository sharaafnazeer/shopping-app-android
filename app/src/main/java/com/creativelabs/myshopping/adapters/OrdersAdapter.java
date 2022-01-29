package com.creativelabs.myshopping.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.creativelabs.myshopping.R;
import com.creativelabs.myshopping.entity.Order;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private List<Order> orderList = new ArrayList<>();

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_one_order, parent, false);
        return new OrdersAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.ViewHolder holder, int position) {
        Order order = orderList.get(position);

        holder.tvOrderID.setText(order.getCode());
        holder.tvOrderDate.setText(order.getOrderDate());
        holder.tvOrderAmount.setText("LKR. " + order.getTotalAmount());
        holder.rbOrderRating.setRating(order.getRating());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvOrderID;
        TextView tvOrderDate;
        TextView tvOrderAmount;
        RatingBar rbOrderRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvOrderID = itemView.findViewById(R.id.tvOrderID);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvOrderAmount = itemView.findViewById(R.id.tvOrderAmount);
            rbOrderRating = itemView.findViewById(R.id.rbOrderRating);
        }
    }
}
