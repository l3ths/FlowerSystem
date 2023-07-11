package com.example.flowersystem.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitClient {
    private static Retrofit instance;
//    private static Object LOCK = new Object();

    public static Retrofit getInstance() {

//        if (instance == null) {
//            synchronized (LOCK) {
//                if (instance == null) {
//                    OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                            .addInterceptor(OAuthInterceptor.getInstance()).build();
//
//                    instance = new Retrofit.Builder().baseUrl(ConnectionConfig.BASE_URL)
//                            .addConverterFactory(JacksonConverterFactory.create())
//                            .client(okHttpClient)
//                            .build();
//                }
//            }
//        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(OAuthInterceptor.getInstance()).build();
        instance = new Retrofit.Builder().baseUrl(ConnectionConfig.BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return instance;
    }
}

class OAuthInterceptor implements Interceptor {

    private String token;
    private static Object LOCK = new Object();

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private static OAuthInterceptor instance;

    public static OAuthInterceptor getInstance() {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new OAuthInterceptor();
                }
            }
        }
        instance.setToken(JwtTokenManager.JWT_TOKEN);
        return instance;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder().addHeader("Authorization", token).build();
        return chain.proceed(request);
    }
}
