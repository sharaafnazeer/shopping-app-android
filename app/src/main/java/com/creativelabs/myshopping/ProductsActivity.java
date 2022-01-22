package com.creativelabs.myshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.creativelabs.myshopping.adapters.ProductsAdapter;
import com.creativelabs.myshopping.entity.Product;
import com.creativelabs.myshopping.utils.ApiInterface;
import com.creativelabs.myshopping.utils.NetworkService;
import com.creativelabs.myshopping.utils.SharedPref;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity {


    RecyclerView rvProducts;
    ProductsAdapter productsAdapter;
    List<Product> productList;

    ApiInterface apiInterface;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        apiInterface = NetworkService.getInstance(SharedPref.getToken(this))
                .getService(ApiInterface.class);

        rvProducts = findViewById(R.id.rvProducts);

        productsAdapter = new ProductsAdapter();

        rvProducts.setLayoutManager(new GridLayoutManager(this, 2));

        rvProducts.setAdapter(productsAdapter);
        productList = new ArrayList<>();

        productsAdapter.setProductList(productList);
        getProducts();

    }

    private void getProducts() {
        progressDialog =  new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("I am fetching your data");
        progressDialog.show();
        apiInterface.getAllProducts(1)
                .enqueue(new Callback<List<Product>>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                        productList = response.body();
                        productsAdapter.setProductList(productList);
                        productsAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Product>> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
    }
}