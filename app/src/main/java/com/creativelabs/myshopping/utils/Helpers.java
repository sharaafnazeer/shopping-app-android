package com.creativelabs.myshopping.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helpers {


    public static boolean checkEmail (String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
