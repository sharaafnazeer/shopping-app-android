package com.creativelabs.myshopping.utils;

import java.util.concurrent.TimeUnit;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    public static final String BASE_URL = "https://7abf-2402-4000-2381-395c-b4cc-96b0-bae7-ded6.ngrok.io/api/";
    public String accessToken;

    public NetworkService(String accessToken) {
        this.accessToken = accessToken;
    }

    public static NetworkService getInstance(String accessToken) {
        return new NetworkService(accessToken);
    }

    public <S> S getService(Class<S> serviceClass) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new BasicAuthItercepter(accessToken));


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                .client(httpClient.build());

        Retrofit retrofit = builder.build();

        return retrofit.create(serviceClass);
    }
}
