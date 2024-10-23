package com.example.cafeonline.api;

import com.example.cafeonline.model.request.AddToCartRequest;
import com.example.cafeonline.model.request.CartDetailRequest;
import com.example.cafeonline.model.request.RegisterRequest;
import com.example.cafeonline.model.response.ApiResponse;
import com.example.cafeonline.model.response.CartResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CartApiService {

    //PHAT
    @POST("api/carts")
    Call<ApiResponse<String>> addToCart(@Body AddToCartRequest addToCartRequest);
    @GET("api/carts/{userId}")
    Call<ApiResponse<CartResponse>> getCart(@Body CartDetailRequest cartDetailRequest);
}
