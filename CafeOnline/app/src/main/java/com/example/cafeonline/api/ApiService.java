package com.example.cafeonline.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public interface ApiService {
        static final String BASE_URL = "https://globalmind.azurewebsites.net/";

    public static <T> T createService(Class<T> serviceClass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) // Add this line
                .build();

        return retrofit.create(serviceClass);
    }
}
