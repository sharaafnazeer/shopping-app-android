package com.creativelabs.myshopping.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.creativelabs.myshopping.R;
import com.creativelabs.myshopping.entity.ActionResponse;
import com.creativelabs.myshopping.entity.Cart;
import com.creativelabs.myshopping.utils.ApiInterface;
import com.creativelabs.myshopping.utils.NetworkService;
import com.creativelabs.myshopping.utils.SharedPref;
import com.mrntlu.toastie.Toastie;
import com.squareup.picasso.Picasso;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private List<Cart> shoppingCartList = new ArrayList<>();
    ApiInterface apiInterface;
    private Context context;
    private ProgressDialog progressDialog;

    public  ShoppingCartAdapter(Context context) {
        this.context = context;
        apiInterface = NetworkService.getInstance(SharedPref.getToken(this.context))
                .getService(ApiInterface.class);
    }

    public void setShoppingCartList(List<Cart> shoppingCartList) {
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

        Cart cart = shoppingCartList.get(position);

        holder.tvItemName.setText(cart.getProductName());
        holder.tvItemPrice.setText(String.format("LKR. %s", cart.getProductPrice()));
        holder.npQuantity.setValue(cart.getQuantity());

        // Handle with Picasso
        Picasso.get()
                .load(cart.getImage())
                .resize(50, 50)
                .centerCrop()
                .error(R.drawable.ic_baseline_shopping_bag_24)
                .into(holder.ivCartItem);

        holder.npQuantity.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {

                progressDialog = new ProgressDialog(context);
                progressDialog.setCancelable(true);
                progressDialog.setTitle("Please wait");
                progressDialog.setMessage("I am fetching your data");
                progressDialog.show();
                cart.setQuantity(value);

                apiInterface.updateCart(cart.getId(), cart)
                        .enqueue(new Callback<ActionResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<ActionResponse> call, @NonNull Response<ActionResponse> response) {

                                assert response.body() != null;
                                Toastie.topSuccess(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onFailure(@NonNull Call<ActionResponse> call, @NonNull Throwable t) {
                                progressDialog.dismiss();

                            }
                        });

                double totalPrice = cart.getProductPrice() * value;
                holder.tvItemPrice.setText(String.format("LKR. %s", totalPrice));
            }
        });

        holder.ibRemoveCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(context);
                progressDialog.setCancelable(true);
                progressDialog.setTitle("Please wait");
                progressDialog.setMessage("I am fetching your data");
                progressDialog.show();

                apiInterface.deleteCart(cart.getId())
                        .enqueue(new Callback<ActionResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<ActionResponse> call, @NonNull Response<ActionResponse> response) {
                                assert response.body() != null;
                                Toastie.topSuccess(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onFailure(@NonNull Call<ActionResponse> call, @NonNull Throwable t) {
                                progressDialog.dismiss();
                            }
                        });
            }
        });
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