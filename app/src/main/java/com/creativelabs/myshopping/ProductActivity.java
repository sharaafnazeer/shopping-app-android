package com.creativelabs.myshopping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.creativelabs.myshopping.entity.ActionResponse;
import com.creativelabs.myshopping.entity.Cart;
import com.creativelabs.myshopping.entity.Product;
import com.creativelabs.myshopping.utils.ApiInterface;
import com.creativelabs.myshopping.utils.NetworkService;
import com.creativelabs.myshopping.utils.SharedPref;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mrntlu.toastie.Toastie;
import com.squareup.picasso.Picasso;
import com.travijuu.numberpicker.library.NumberPicker;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {

    private int productId = -1;
    ApiInterface apiInterface;
    ProgressDialog progressDialog;
    private Product product;

    ImageView ivProductImage;
    TextView tvProductOneName, tvProductOnePrice, tvProductOneDescription;
    Button btnAddToCart;
    NumberPicker npProductOneQuantity;

    CollapsingToolbarLayout toolBarLayout;
    NestedScrollView nsRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBarLayout = findViewById(R.id.toolbar_layout);

        apiInterface = NetworkService.getInstance(SharedPref.getToken(this))
                .getService(ApiInterface.class);

        productId = getIntent().getIntExtra("PRODUCT_ID", -1);

        ivProductImage = findViewById(R.id.ivProductImage);
        tvProductOneName = findViewById(R.id.tvProductOneName);
        tvProductOnePrice = findViewById(R.id.tvProductOnePrice);
        tvProductOneDescription = findViewById(R.id.tvProductOneDescription);

        nsRoot = findViewById(R.id.nsRoot);

        btnAddToCart = findViewById(R.id.btnAddToCart);
        npProductOneQuantity = findViewById(R.id.npProductOneQuantity);

        if (productId > 0) {
            toolBarLayout.setTitle("");
            getProduct();

        } else {
            toolBarLayout.setTitle("N/A");
        }

        btnAddToCart.setOnClickListener(view -> {

            int quantity = npProductOneQuantity.getValue();

            Cart cart = new Cart();
            cart.setQuantity(quantity);
            cart.setProductId(product.getId());
            addToCart(cart);
        });
    }

    private void getProduct() {
        progressDialog =  new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("I am fetching your data");
        progressDialog.show();
        apiInterface.getOneProduct(productId)
                .enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(@NonNull Call<Product> call, @NonNull Response<Product> response) {
                        product = response.body();

                        assert product != null;
                        tvProductOneName.setText(String.format("%s (%s)", product.getName(), product.getCode()));
                        tvProductOnePrice.setText(String.format("LKR. %s", product.getPrice()));
                        tvProductOneDescription.setText(product.getDescription());
                        toolBarLayout.setTitle(product.getName());

                        // Handle with Picasso
                        Picasso.get()
                                .load(product.getImage())
                                .error(R.drawable.ic_baseline_shopping_bag_24)
                                .into(ivProductImage);


                        ivProductImage.setVisibility(View.VISIBLE);
                        nsRoot.setVisibility(View.VISIBLE);
                        toolBarLayout.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<Product> call, @NonNull Throwable t) {
                        progressDialog.dismiss();
                    }
                });
    }

    private void addToCart(Cart cart) {
        progressDialog =  new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("I am fetching your data");
        progressDialog.show();
        apiInterface.addToCart(cart)
                .enqueue(new Callback<ActionResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ActionResponse> call, @NonNull Response<ActionResponse> response) {
                        ActionResponse message = response.body();
                        assert message != null;
                        Toastie.topSuccess(getApplicationContext(), message.getMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        finish();
                    }

                    @Override
                    public void onFailure(@NonNull Call<ActionResponse> call, @NonNull Throwable t) {
                        progressDialog.dismiss();
                    }
                });
    }
}