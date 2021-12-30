package com.creativelabs.myshopping;

import static com.creativelabs.myshopping.utils.Helpers.checkEmail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.creativelabs.myshopping.utils.SharedPref;
import com.mrntlu.toastie.Toastie;

public class LoginActivity extends AppCompatActivity {

    String userName = "admin";
    String password = "qwerty";

    // Java int string float char boolean
    // 2 Edit Text, Button 2, TextView

    EditText etEmail; // Variable declaration
    EditText etPassword;
    Button btnLogin;
    Button btnNewAccount;
    TextView tvForgotPassword;

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
                SharedPref.setIsLoggedIn(this, true);
                Intent homeIntent = new Intent(this, MainActivity.class);
                startActivity(homeIntent);
                finish();
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
        } else if (!(userName.equals(uname) && password.equals(pass))) {
            Toastie.topError(this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


}