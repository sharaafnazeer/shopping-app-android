package com.creativelabs.myshopping.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.creativelabs.myshopping.LoginActivity;
import com.creativelabs.myshopping.R;
import com.creativelabs.myshopping.adapters.OrderPageAdapter;
import com.creativelabs.myshopping.utils.SharedPref;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdersFragment newInstance(String param1, String param2) {
        OrdersFragment fragment = new OrdersFragment();
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

    ViewPager2 vpOrders;
    OrderPageAdapter orderPageAdapter;
    TabLayout tlOrderTabs;
    ConstraintLayout vNotLoggedIn;
    private String[] titleArray = {"All Orders", "Completed", "Cancelled"};
    Button btnGoLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        vpOrders = view.findViewById(R.id.vpOrders);
        vNotLoggedIn = view.findViewById(R.id.vNotLoggedIn);
        tlOrderTabs = view.findViewById(R.id.tlOrderTabs);

        if (isLoggedIn()) {
            vpOrders.setVisibility(View.VISIBLE);
            tlOrderTabs.setVisibility(View.VISIBLE);
            vNotLoggedIn.setVisibility(View.GONE);
        } else {
            vpOrders.setVisibility(View.GONE);
            tlOrderTabs.setVisibility(View.GONE);
            vNotLoggedIn.setVisibility(View.VISIBLE);
        }

        btnGoLogin = view.findViewById(R.id.btnGoLogin);

        btnGoLogin.setOnClickListener(v -> {
            Intent loginIntent  = new Intent(getContext(), LoginActivity.class);
            startActivity(loginIntent);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orderPageAdapter = new OrderPageAdapter(this);
        vpOrders.setAdapter(orderPageAdapter);
        new TabLayoutMediator(tlOrderTabs, vpOrders,
                (tab, position) -> tab.setText(titleArray[position])
        ).attach();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLoggedIn()) {
            vpOrders.setVisibility(View.VISIBLE);
            tlOrderTabs.setVisibility(View.VISIBLE);
            vNotLoggedIn.setVisibility(View.GONE);
        } else {
            vpOrders.setVisibility(View.GONE);
            tlOrderTabs.setVisibility(View.GONE);
            vNotLoggedIn.setVisibility(View.VISIBLE);
        }
    }

    private boolean isLoggedIn() {
        return SharedPref.getIsLoggedIn(getContext());
    }
}