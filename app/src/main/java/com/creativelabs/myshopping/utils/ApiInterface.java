package com.creativelabs.myshopping.utils;

import com.creativelabs.myshopping.entity.ActionResponse;
import com.creativelabs.myshopping.entity.Auth;
import com.creativelabs.myshopping.entity.AuthResponse;
import com.creativelabs.myshopping.entity.Cart;
import com.creativelabs.myshopping.entity.Category;
import com.creativelabs.myshopping.entity.Order;
import com.creativelabs.myshopping.entity.Product;
import com.creativelabs.myshopping.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    // Category Related

    @GET("category")
    Call<List<Category>> getAllCategories();

    @GET("category/{id}")
    Call<List<Category>> getOneCategory(@Path("id") int categoryId);

    // Product Related

    @GET("product")
    Call<List<Product>> getAllProducts(@Query("categoryId") int categoryId);

    @GET("product/{id}")
    Call<Product> getOneProduct(@Path("id") int productId);

    // Cart Related

    @GET("cart")
    Call<List<Cart>> getAllCarts();

    @POST("cart")
    Call<ActionResponse> addToCart(@Body Cart cart);

    @PUT("cart/{id}")
    Call<ActionResponse> updateCart(@Path("id") int cartId, @Body Cart cart);

    @DELETE("cart/{id}")
    Call<ActionResponse> deleteCart(@Path("id") int cartId);

    // Order Related

    @GET("order")
    Call<List<Order>> getAllOrders(@Query("status") int status);

    @POST("order")
    Call<ActionResponse> saveOrder();

    // Auth Related

    @POST("auth/login")
    Call<AuthResponse> login(@Body Auth user);

    @POST("auth/register")
    Call<User> register(@Body User user);
}
