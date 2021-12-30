package com.creativelabs.myshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mrntlu.toastie.Toastie;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextInputLayout tiCode,tiNewPassword, tiConfirmPassword;
    TextInputEditText etCode, etNewPassword, etConfirmPassword;

    Button btnResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        tiCode = findViewById(R.id.tiCode);
        tiNewPassword = findViewById(R.id.tiNewPassword);
        tiConfirmPassword = findViewById(R.id.tiConfirmPassword);

        etCode = findViewById(R.id.etCode);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnResetPassword = findViewById(R.id.btnResetPassword);

        btnResetPassword.setOnClickListener(v -> {
            if (validate()) {
                // TODO Reset password
            }
        });
    }

    private boolean validate(){

        boolean valid = true;

        if (TextUtils.isEmpty(etCode.getText())) {
            valid = false;
            Toastie.topError(this, "Please enter your verification code", Toast.LENGTH_LONG).show();
        }

        //TODO Need to check the validity of verification code via a network call

        if (TextUtils.isEmpty(etNewPassword.getText())) {
            valid = false;
            Toastie.topError(this, "Please enter your new password", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(etConfirmPassword.getText())) {
            valid = false;
            Toastie.topError(this, "Please enter your new confirm password", Toast.LENGTH_LONG).show();
        }
        if(!Objects.requireNonNull(etNewPassword.getText()).equals(etConfirmPassword.getText())) {
            valid = false;
            Toastie.topError(this, "Your confirm password must be same as the password", Toast.LENGTH_LONG).show();
        }

        return valid;
    }
}