package com.creativelabs.myshopping.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.creativelabs.myshopping.ProductActivity;
import com.creativelabs.myshopping.R;
import com.creativelabs.myshopping.entity.Order;
import com.creativelabs.myshopping.entity.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private List<Product> productList = new ArrayList<>();

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_one_product, parent, false);
        return new ProductsAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.tvProductName.setText(product.getName());
        holder.tvProductPrice.setText("LKR. " + product.getPrice());

        // Handle with Picasso
        Picasso.get()
                .load(product.getImage())
                .error(R.drawable.ic_baseline_shopping_bag_24)
                .into(holder.ivProduct);

        holder.cvRoot.setOnClickListener(view -> {
            Intent productIntent = new Intent(view.getContext(), ProductActivity.class);
            productIntent.putExtra("PRODUCT_ID", product.getId());
            view.getContext().startActivity(productIntent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvProductName;
        TextView tvProductPrice;
        ImageView ivProduct;
        CardView cvRoot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            ivProduct = itemView.findViewById(R.id.ivProduct);
            cvRoot = itemView.findViewById(R.id.cvRoot);
        }
    }
}