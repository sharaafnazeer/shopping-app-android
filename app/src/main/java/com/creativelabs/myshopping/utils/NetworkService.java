package com.creativelabs.myshopping.utils;


import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    public static final String BASE_URL = "https://e350-2402-4000-2380-3948-7eab-8027-422-6e0b.ngrok.io/api/";
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
//                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(httpClient.build());

        Retrofit retrofit = builder.build();

        return retrofit.create(serviceClass);
    }
}