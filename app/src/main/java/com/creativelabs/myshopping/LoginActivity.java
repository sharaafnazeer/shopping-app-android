package com.creativelabs.myshopping;

import static com.creativelabs.myshopping.utils.Helpers.checkEmail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.creativelabs.myshopping.entity.Auth;
import com.creativelabs.myshopping.entity.AuthResponse;
import com.creativelabs.myshopping.utils.ApiInterface;
import com.creativelabs.myshopping.utils.NetworkService;
import com.creativelabs.myshopping.utils.SharedPref;
import com.mrntlu.toastie.Toastie;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
<<<<<<< HEAD

public class LoginActivity extends AppCompatActivity {
=======

public class LoginActivity extends AppCompatActivity {

>>>>>>> c4bf4969c20a0f0bc159ac1d330e58e16d5ed054

    // Java int string float char boolean
    // 2 Edit Text, Button 2, TextView

    EditText etEmail; // Variable declaration
    EditText etPassword;
    Button btnLogin;
    Button btnNewAccount;
    TextView tvForgotPassword;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login_linear); // 1 . Linear Layout
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnRegister);
        btnNewAccount = findViewById(R.id.btnCreateNewAccount);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        btnLogin.setOnClickListener(v -> {
            // Logic

            // Validate username and password
            // 1. Check for empty or null values
            // 2 . Check for credentials
            String uname = etEmail.getText().toString();
            String pass = etPassword.getText().toString();

            if (validate(uname, pass)) {
<<<<<<< HEAD
                Auth auth = new Auth();
                auth.setEmail(uname);
                auth.setPassword(pass);
=======

                Auth auth = new Auth();
                auth.setEmail(uname);
                auth.setPassword(pass);

>>>>>>> c4bf4969c20a0f0bc159ac1d330e58e16d5ed054
                login(auth);
            }
        });

        btnNewAccount.setOnClickListener(v -> {
            Intent registerIntent = new Intent(this, RegisterActivity.class);
            startActivity(registerIntent);
        });

        tvForgotPassword.setOnClickListener(v -> {

            if (TextUtils.isEmpty(etEmail.getText())) {
                Toastie.topError(this, "Username is empty", Toast.LENGTH_LONG).show();
            } else if (checkEmail(etEmail.getText().toString())) {
                Toastie.topError(this, "Please enter a valid email", Toast.LENGTH_LONG).show();
            } else {
                Intent forgotIntent = new Intent(this, ForgotPasswordActivity.class);
                startActivity(forgotIntent);
            }
        });

<<<<<<< HEAD
        apiInterface = NetworkService.getInstance(SharedPref.getToken(this))
                .getService(ApiInterface.class);
=======
        apiInterface = NetworkService.getInstance(SharedPref.getToken(this)).getService(ApiInterface.class);
>>>>>>> c4bf4969c20a0f0bc159ac1d330e58e16d5ed054
    }

    private boolean validate(String uname, String pass) {
        if (TextUtils.isEmpty(uname)) {
            Toastie.topError(this, "Username is empty", Toast.LENGTH_LONG).show();
            return false;
        } else if (!checkEmail(uname)) {
            Toastie.topError(this, "Please enter a valid email", Toast.LENGTH_LONG).show();
            return false;
        } else if (TextUtils.isEmpty(pass)) {
            Toastie.topError(this, "Password is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void login(Auth auth) {

        apiInterface.login(auth)
                .enqueue(new Callback<AuthResponse>() {
                    @Override
<<<<<<< HEAD
                    public void onResponse(@NonNull Call<AuthResponse> call, @NonNull Response<AuthResponse> response) {
                        SharedPref.setIsLoggedIn(getApplicationContext(), true);

                        assert response.body() != null;
                        SharedPref.setToken(getApplicationContext(), response.body().getJwt());
                        Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(homeIntent);
                        finish();
                        Log.d("LOGIN", "SUCCESS");
                    }

                    @Override
                    public void onFailure(@NonNull Call<AuthResponse> call, @NonNull Throwable t) {
                        Log.d("LOGIN", "FAILED");
                    }
                });
    }
=======
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        SharedPref.setIsLoggedIn(getApplicationContext(), true);
                        assert response.body() != null;
                        SharedPref.setToken(getApplicationContext(), response.body().getJwt());
//                        Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(homeIntent);
                        finish();
                        Log.d("LOGIN", "SUCCESS");
                        Log.d("LOGIN", SharedPref.getToken(getApplicationContext()));
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        Log.d("LOGIN", "FAILED");
                    }
                });

    }

>>>>>>> c4bf4969c20a0f0bc159ac1d330e58e16d5ed054

}