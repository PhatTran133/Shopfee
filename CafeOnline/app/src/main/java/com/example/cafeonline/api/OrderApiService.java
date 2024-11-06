package com.example.cafeonline.api;

import com.example.cafeonline.model.request.OrderRequestModel;
import com.example.cafeonline.model.response.ApiResponse;
import com.example.cafeonline.model.response.OrderResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrderApiService {

    //Anh nhac em
    @GET("/api/orders/status/{userId}")
    Call<ApiResponse<OrderResponse>> getOrder(@Path("userId") int userId,
                                              @Query("orderStatus") String orderStatus);
    @POST("/api/orders")
    Call<ApiResponse<Integer>> createOrder(@Body OrderRequestModel orderRequestModel);
    @GET("/api/orders/{orderId}")
    Call<ApiResponse<OrderResponse>> getOrderById(@Path("orderId") int orderId);

}
