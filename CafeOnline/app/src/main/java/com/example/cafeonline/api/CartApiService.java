package com.example.cafeonline.api;

import com.example.cafeonline.model.request.AddToCartRequest;
import com.example.cafeonline.model.request.CartDetailRequest;
import com.example.cafeonline.model.request.RegisterRequest;
import com.example.cafeonline.model.response.ApiResponse;
import com.example.cafeonline.model.response.CartResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CartApiService {

    //PHAT
    @POST("api/carts")
    Call<ApiResponse<String>> addToCart(@Body AddToCartRequest addToCartRequest);
    @GET("api/carts/{userId}")
    Call<ApiResponse<CartResponse>> getCart(@Path("userId") int userId);
    @DELETE("/api/carts/{cartToppingDrinkId}")
    Call<ApiResponse<String>> deleteCartItems(@Path("cartToppingDrinkId") int cartToppingDrinkId);
}
