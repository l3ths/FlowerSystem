package com.example.flowersystem.api;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitClient {
    private static Retrofit instance;
    private static Object LOCK = new Object();

    public static Retrofit getInstance() {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new Retrofit.Builder().baseUrl(ConnectionConfig.BASE_URL)
                            .addConverterFactory(JacksonConverterFactory.create())
                            .build();
                }
            }
        }
        return instance;
    }
}
