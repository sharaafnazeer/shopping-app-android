package com.creativelabs.myshopping.utils;


import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    public static final String BASE_URL = "https://fb20-2402-4000-2280-19e8-669d-3159-6058-5be8.ngrok.io/api/";
    public String accessToken;

    private NetworkService() {
    }

    private NetworkService(String accessToken) {
        this.accessToken = accessToken;
    }

    public static NetworkService getInstance(String accessToken){
        return new NetworkService(accessToken);
    }

    public static NetworkService getInstance(){
        return new NetworkService();
    }

    public <S> S getService(Class<S> serviceClass) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new BasicAuthIntercepter(accessToken));




        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(httpClient.build());

        Retrofit retrofit = builder.build();

        return retrofit.create(serviceClass);
    }
}