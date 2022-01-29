package com.creativelabs.myshopping.utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BasicAuthIntercepter implements Interceptor {

    private String accessToken;

    public BasicAuthIntercepter(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatedRequest;

        authenticatedRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer " + this.accessToken)
//                .addHeader("Accept:", "application/json")
                .build();

        return chain.proceed(authenticatedRequest);
    }
}
