package com.creativelabs.myshopping.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.creativelabs.myshopping.R;
import com.creativelabs.myshopping.entity.ShoppingCart;
import com.squareup.picasso.Picasso;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private List<ShoppingCart> shoppingCartList = new ArrayList<>();

    public void setShoppingCartList(List<ShoppingCart> shoppingCartList) {
        this.shoppingCartList = shoppingCartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cart_item, parent, false);
        return new ShoppingCartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartAdapter.ViewHolder holder, int position) {

        ShoppingCart cart = shoppingCartList.get(position);

        holder.tvItemName.setText(cart.getItemName());
        holder.tvItemPrice.setText(String.format("LKR. %s", cart.getPrice()));
        holder.npQuantity.setValue(cart.getQuantity());

        // Handle with Picasso
        Picasso.get()
                .load(cart.getImage())
                .resize(50, 50)
                .centerCrop()
                .error(R.drawable.ic_baseline_shopping_bag_24)
                .into(holder.ivCartItem);
    }

    @Override
    public int getItemCount() {
        return shoppingCartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivCartItem;
        TextView tvItemName;
        TextView tvItemPrice;
        ImageButton ibRemoveCartItem;
        NumberPicker npQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivCartItem = itemView.findViewById(R.id.ivCartItem);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemPrice = itemView.findViewById(R.id.tvItemPrice);
            ibRemoveCartItem = itemView.findViewById(R.id.ibRemoveCartItem);
            npQuantity = itemView.findViewById(R.id.npQuantity);
        }
    }
}
