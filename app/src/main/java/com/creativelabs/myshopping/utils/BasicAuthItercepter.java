package com.creativelabs.myshopping.utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BasicAuthItercepter implements Interceptor {

    private String accessToken;

    public BasicAuthItercepter(String accessToken) {
        this.accessToken = accessToken;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatedRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        return chain.proceed(authenticatedRequest);
    }
}
