package com.vk2.touchsreentab.api;

import android.util.Log;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    public static ApiService apiService;
    private static Api instance = null;
    private static Retrofit retrofit;

    public static Api getInstance() {
        if (instance == null) {
            instance = new Api();
        }
        return instance;
    }

    public ApiService create(String baseUrl) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(new RequestInterceptor());
        builder.connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);
        OkHttpClient okHttpClient = builder.build();

        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();

            apiService = retrofit.create(ApiService.class);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("API:", "Cannot connect to server!");
        }
        return apiService;
    }
}