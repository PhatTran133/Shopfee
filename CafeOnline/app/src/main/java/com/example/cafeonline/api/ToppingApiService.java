package com.example.cafeonline.api;

import com.example.cafeonline.model.response.ApiResponse;
import com.example.cafeonline.model.response.ToppingResponse;
import com.example.cafeonline.model.response.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ToppingApiService {

    //PHAT
    @GET("/api/Topping")
    Call<ApiResponse<List<ToppingResponse>>> getTopping();
}
