package com.creativelabs.myshopping.fragments;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.creativelabs.myshopping.R;
import com.creativelabs.myshopping.adapters.CategoriesAdapter;
import com.creativelabs.myshopping.adapters.ProductsAdapter;
import com.creativelabs.myshopping.entity.Category;
import com.creativelabs.myshopping.entity.Product;
import com.creativelabs.myshopping.utils.ApiInterface;
import com.creativelabs.myshopping.utils.NetworkService;
import com.creativelabs.myshopping.utils.SharedPref;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    RecyclerView rvCategories;
    CategoriesAdapter categoriesAdapter;
    List<Category> categories = new ArrayList<>();
    ApiInterface apiInterface;
    ProgressDialog progressDialog;
    CarouselView crvBanners;


    List<Product> bestSellers = new ArrayList<>();
    ProductsAdapter productsAdapter;
    RecyclerView rvBestSellers;


    String[] images = {
            "https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/iphone11-select-2019-family?wid=882&hei=1058&fmt=jpeg&qlt=80&.v=1567022175704",
            "https://www.notebookcheck.net/uploads/tx_nbc2/4_to_3_Teaser_Apple_iPhone_13_Pro.jpg",
            "https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/macbook-air-gold-select-201810?wid=904&hei=840&fmt=jpeg&qlt=80&.v=1633027804000",
            "https://www.cnet.com/a/img/At7JXOb2erGg-eOdKylQhFKfeJY=/2021/10/23/80425069-0d3e-4c67-9085-a66e6177fc60/macbook-pro-2021-cnet-review-12.jpg",
            "https://www.singersl.com/sites/default/files/images/products/2020-01/SIN_FAN-DFP500T-01_zoom.jpg",

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvCategories = view.findViewById(R.id.rvCategory);
        rvBestSellers = view.findViewById(R.id.rvBestSellers);
        crvBanners = view.findViewById(R.id.crvBanners);

        crvBanners.setPageCount(images.length);
        crvBanners.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {

                Picasso.get()
                        .load(images[position])
                        .error(R.drawable.ic_baseline_shopping_bag_24)
                        .into(imageView);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiInterface = NetworkService.getInstance(SharedPref.getToken(getContext())).getService(ApiInterface.class);
        categoriesAdapter = new CategoriesAdapter();
        rvCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvCategories.setAdapter(categoriesAdapter);

        categoriesAdapter.setCategories(categories);



        productsAdapter = new ProductsAdapter();
        rvBestSellers.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvBestSellers.setAdapter(productsAdapter);

        productsAdapter.setProductList(bestSellers);

        getCategories();
        getBestSellers();

    }

    private void getCategories() {
        progressDialog =  new ProgressDialog(getContext());
        progressDialog.setCancelable(true);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("I am fetching your data");
        progressDialog.show();
        apiInterface.getAllCategories()
                .enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {

                        List<Category> categoryList
                                = response.body();
                        categoriesAdapter.setCategories(categoryList);
                        categoriesAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                        Log.d("CATEGORIES", "SUCCESS");
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable t) {
                        progressDialog.dismiss();
                        Log.d("CATEGORIES", "FAILED");
                    }
                });
    }

    private void getBestSellers() {
        apiInterface.getAllProducts(-1)
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {

                        List<Product> _bestSellers = new ArrayList<>();

                        List<Integer> givenList = Arrays.asList(1, 2, 3, 4, 5);
                        Random rand = new Random();
                        int randomElement = givenList.get(rand.nextInt(givenList.size()));

                        for (int i = 0; i < 6; i++) {
                            _bestSellers.add(response.body().get(randomElement));
                        }

                        productsAdapter.setProductList(_bestSellers);
                        productsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {

                    }
                });
    }
}