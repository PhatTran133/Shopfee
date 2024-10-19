package com.example.cafeonline.api;

import com.example.cafeonline.model.request.AddToCartRequest;
import com.example.cafeonline.model.request.RegisterRequest;
import com.example.cafeonline.model.response.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CartApiService {

    //PHAT
    @POST("api/carts")
    Call<ApiResponse<String>> addToCart(@Body AddToCartRequest addToCartRequest);
}
