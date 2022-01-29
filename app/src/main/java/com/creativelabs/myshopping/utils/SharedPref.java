package com.creativelabs.myshopping.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private SharedPreferences preferences;

    @SuppressLint("WrongConstant")
    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
    }

    public static void setIsLoggedIn(Context context, boolean isLoggedIn) {
        getPreferences(context).edit().putBoolean(Constants.IS_LOGGED_IN, isLoggedIn).apply();
    }

    public static boolean getIsLoggedIn(Context context) {
        return getPreferences(context).getBoolean(Constants.IS_LOGGED_IN, false);
    }

    public static void setToken(Context context, String token) {
        getPreferences(context).edit().putString(Constants.TOKEN, token).apply();
    }

    public static String getToken(Context context) {
        return getPreferences(context).getString(Constants.TOKEN, null);
    }
}
