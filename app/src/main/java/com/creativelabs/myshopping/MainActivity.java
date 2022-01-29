package com.creativelabs.myshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import com.creativelabs.myshopping.entity.ActionResponse;
import com.creativelabs.myshopping.fragments.HomeFragment;
import com.creativelabs.myshopping.fragments.MyAccountFragment;
import com.creativelabs.myshopping.fragments.OrdersFragment;
import com.creativelabs.myshopping.fragments.ShoppingCartFragment;
<<<<<<< HEAD
=======
import com.creativelabs.myshopping.utils.ApiInterface;
import com.creativelabs.myshopping.utils.NetworkService;
>>>>>>> c4bf4969c20a0f0bc159ac1d330e58e16d5ed054
import com.creativelabs.myshopping.utils.SharedPref;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bnVMain;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnVMain = findViewById(R.id.bnvMain);
        showHomeView();

        bnVMain.setOnNavigationItemSelectedListener(this);

<<<<<<< HEAD
        Log.d("TOKEN", SharedPref.getToken(this));
=======
        String token = SharedPref.getToken(this);

        //Log.d("TOKEN", SharedPref.getToken(this));

        apiInterface = NetworkService.getInstance(SharedPref.getToken(this))
                .getService(ApiInterface.class);

        checkAuthorized();
>>>>>>> c4bf4969c20a0f0bc159ac1d330e58e16d5ed054
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

    private void checkAuthorized() {

        apiInterface.isAuthorized()
                .enqueue(new Callback<ActionResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ActionResponse> call, @NonNull Response<ActionResponse> response) {
                        SharedPref.setIsLoggedIn(getApplicationContext(), true);
                        Log.d("IS_AUTH", "SUCCESS");
                    }

                    @Override
                    public void onFailure(@NonNull Call<ActionResponse> call, @NonNull Throwable t) {
                        SharedPref.setIsLoggedIn(getApplicationContext(), false);
                        Log.d("IS_AUTH", "FAILEd");
                    }
                });

    }
}