package com.creativelabs.myshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import com.creativelabs.myshopping.fragments.HomeFragment;
import com.creativelabs.myshopping.fragments.MyAccountFragment;
import com.creativelabs.myshopping.fragments.OrdersFragment;
import com.creativelabs.myshopping.fragments.ShoppingCartFragment;
import com.creativelabs.myshopping.utils.SharedPref;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bnVMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnVMain = findViewById(R.id.bnvMain);
        showHomeView();

        bnVMain.setOnNavigationItemSelectedListener(this);

        String token = SharedPref.getToken(this);

        //Log.d("TOKEN", SharedPref.getToken(this));
    }

    private void showHomeView() {
        getSupportFragmentManager().beginTransaction().add(R.id.flContent, new HomeFragment()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.menu_item_home:
                fragment = new HomeFragment();
                break;

            case R.id.menu_item_orders:
                fragment = new OrdersFragment();
                break;

            case R.id.menu_item_cart:
                fragment = new ShoppingCartFragment();
                break;

            case R.id.menu_item_account:
                fragment = new MyAccountFragment();
                break;
            default:
                fragment = new HomeFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fragment).commit();
        return true;
    }
}