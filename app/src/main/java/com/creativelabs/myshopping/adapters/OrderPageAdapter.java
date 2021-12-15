package com.creativelabs.myshopping.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.creativelabs.myshopping.fragments.order.AllOrdersFragment;
import com.creativelabs.myshopping.fragments.order.CanceledOrdersFragment;
import com.creativelabs.myshopping.fragments.order.CompletedOrdersFragment;

public class OrderPageAdapter extends FragmentStateAdapter {

    public OrderPageAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return getActualFragment(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    private Fragment getActualFragment(int position) {
        switch (position) {
            case 0:
                return new AllOrdersFragment();
            case 1:
                return new CompletedOrdersFragment();
            case 2:
                return new CanceledOrdersFragment();
            default:
                return new AllOrdersFragment();

        }
    }
}
