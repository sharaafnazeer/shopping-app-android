package com.creativelabs.myshopping.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.creativelabs.myshopping.LoginActivity;
import com.creativelabs.myshopping.R;
import com.creativelabs.myshopping.utils.SharedPref;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mrntlu.toastie.Toastie;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyAccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyAccountFragment newInstance(String param1, String param2) {
        MyAccountFragment fragment = new MyAccountFragment();
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

    Button btnAccountUpdate, btnGoLogin;
    TextInputEditText etFirstName, etEmail, etPhoneNumber, etAddress;
    TextInputLayout tiFirstName, tiEmail, tiPhoneNumber, tiAddress;
    NestedScrollView nsAccount;
    ConstraintLayout vNotLoggedIn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);

        btnAccountUpdate = view.findViewById(R.id.btnAccountUpdate);
        etFirstName = view.findViewById(R.id.etFirstName);
        etEmail = view.findViewById(R.id.etEmail);
        etPhoneNumber = view.findViewById(R.id.etPhoneNumber);
        etAddress = view.findViewById(R.id.etAddress);


        tiFirstName = view.findViewById(R.id.tiFirstName);
        tiEmail = view.findViewById(R.id.tiEmail);
        tiPhoneNumber = view.findViewById(R.id.tiPhoneNumber);
        tiAddress = view.findViewById(R.id.tiAddress);

        vNotLoggedIn = view.findViewById(R.id.vNotLoggedIn);
        nsAccount = view.findViewById(R.id.nsAccount);

        btnAccountUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update Details

                if (validate(view)) {
                    // Sent a network call to update the details
                    Toastie.topSuccess(getContext(),"Your details updated successfully!", Toast.LENGTH_LONG).show();
                }
            }
        });

        if (isLoggedIn()) {
            nsAccount.setVisibility(View.VISIBLE);
            vNotLoggedIn.setVisibility(View.GONE);
        } else {
            nsAccount.setVisibility(View.GONE);
            vNotLoggedIn.setVisibility(View.VISIBLE);
        }

        btnGoLogin = view.findViewById(R.id.btnGoLogin);

        btnGoLogin.setOnClickListener(v -> {
            Intent loginIntent = new Intent(getContext(), LoginActivity.class);
            startActivity(loginIntent);
        });

        return view;
    }

    private boolean validate(View view) {

        boolean success = true;
        if (TextUtils.isEmpty(etFirstName.getText().toString())) {
            success = false;
            tiFirstName.setError("Enter your first name");
        }

        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            success = false;
            tiEmail.setError("Enter your email");
        }

        if (!checkEmail(etEmail.getText().toString())) {
            success = false;
            tiEmail.setError("Enter a valid email");
        }

        if (TextUtils.isEmpty(etPhoneNumber.getText().toString())) {
            success = false;
            tiPhoneNumber.setError("Enter your phone number");
        }

        if (TextUtils.isEmpty(etAddress.getText().toString())) {
            tiAddress.setError("Enter your address");
            success = false;
        }
        return success;
    }

    private boolean passwordMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    private boolean checkEmail (String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private boolean isLoggedIn() {
        return SharedPref.getIsLoggedIn(getContext());
    }
}