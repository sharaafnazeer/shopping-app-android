package com.creativelabs.myshopping.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.creativelabs.myshopping.LoginActivity;
import com.creativelabs.myshopping.R;
import com.creativelabs.myshopping.adapters.ShoppingCartAdapter;
import com.creativelabs.myshopping.entity.Cart;
import com.creativelabs.myshopping.utils.ApiInterface;
import com.creativelabs.myshopping.utils.NetworkService;
import com.creativelabs.myshopping.utils.SharedPref;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShoppingCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingCartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShoppingCartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingCartFragment newInstance(String param1, String param2) {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView rvShoppingCart;
    ConstraintLayout vNotLoggedIn;
    ShoppingCartAdapter shoppingCartAdapter;
    List<Cart> shoppingCartList;
    Button btnGoLogin;

    ApiInterface apiInterface;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        vNotLoggedIn = view.findViewById(R.id.vNotLoggedIn);
        rvShoppingCart = view.findViewById(R.id.rvShoppingCart);

        if (isLoggedIn()) {
            rvShoppingCart.setVisibility(View.VISIBLE);
            vNotLoggedIn.setVisibility(View.GONE);
        } else {
            rvShoppingCart.setVisibility(View.GONE);
            vNotLoggedIn.setVisibility(View.VISIBLE);
        }

        btnGoLogin = view.findViewById(R.id.btnGoLogin);

        btnGoLogin.setOnClickListener(v -> {
            Intent loginIntent = new Intent(getContext(), LoginActivity.class);
            startActivity(loginIntent);
        });
        apiInterface = NetworkService.getInstance(SharedPref.getToken(getContext()))
                .getService(ApiInterface.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shoppingCartAdapter = new ShoppingCartAdapter(getContext());
        rvShoppingCart.setLayoutManager(new LinearLayoutManager(getContext()));

        rvShoppingCart.setAdapter(shoppingCartAdapter);
        shoppingCartList = new ArrayList<>();

        shoppingCartAdapter.setShoppingCartList(shoppingCartList);

        getCarts();
    }

    private boolean isLoggedIn() {
        return SharedPref.getIsLoggedIn(getContext());
    }

    private void getCarts() {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(true);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("I am fetching your data");
        progressDialog.show();
        apiInterface.getAllCarts()
                .enqueue(new Callback<List<Cart>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Cart>> call, @NonNull Response<List<Cart>> response) {
                        shoppingCartList = response.body();

                        shoppingCartAdapter.setShoppingCartList(shoppingCartList);
                        shoppingCartAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Cart>> call, @NonNull Throwable t) {
                        progressDialog.dismiss();
                    }
                });
    }
}