package com.creativelabs.myshopping.fragments.order;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creativelabs.myshopping.R;
import com.creativelabs.myshopping.adapters.OrdersAdapter;
import com.creativelabs.myshopping.entity.Cart;
import com.creativelabs.myshopping.entity.Order;
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
 * Use the {@link AllOrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllOrdersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AllOrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllOrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllOrdersFragment newInstance(String param1, String param2) {
        AllOrdersFragment fragment = new AllOrdersFragment();
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

    RecyclerView rvAllOrders;
    OrdersAdapter ordersAdapter;
    List<Order> orderList;

    ApiInterface apiInterface;

    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_orders, container, false);
        rvAllOrders = view.findViewById(R.id.rvAllOrders);

        apiInterface = NetworkService.getInstance(SharedPref.getToken(getContext()))
                .getService(ApiInterface.class);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ordersAdapter = new OrdersAdapter();
        rvAllOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        rvAllOrders.setAdapter(ordersAdapter);
        orderList = new ArrayList<>();
        ordersAdapter.setOrderList(orderList);

        getAllOrders();
    }

    private void getAllOrders() {
        progressDialog =  new ProgressDialog(getContext());
        progressDialog.setCancelable(true);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("I am fetching your data");
        progressDialog.show();
        apiInterface.getAllOrders(-1)
                .enqueue(new Callback<List<Order>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Order>> call, @NonNull Response<List<Order>> response) {
                        orderList = response.body();
                        ordersAdapter.setOrderList(orderList);
                        ordersAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Order>> call, @NonNull Throwable t) {
                        progressDialog.dismiss();
                    }
                });
    }
}