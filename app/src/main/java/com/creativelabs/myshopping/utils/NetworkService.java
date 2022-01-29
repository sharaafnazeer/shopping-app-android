package com.creativelabs.myshopping.utils;

<<<<<<< HEAD
import java.util.concurrent.TimeUnit;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
=======

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

>>>>>>> c4bf4969c20a0f0bc159ac1d330e58e16d5ed054
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

<<<<<<< HEAD
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
=======
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
>>>>>>> c4bf4969c20a0f0bc159ac1d330e58e16d5ed054

        Retrofit retrofit = builder.build();

        return retrofit.create(serviceClass);
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> c4bf4969c20a0f0bc159ac1d330e58e16d5ed054
