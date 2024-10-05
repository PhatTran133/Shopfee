package com.example.cafeonline.api;

import com.example.cafeonline.model.request.LoginRequest;
import com.example.cafeonline.model.response.ApiResponse;
import com.example.cafeonline.model.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiService {

    @POST("api/v1/login")
    Call<ApiResponse<UserResponse>> loginUser(@Body LoginRequest loginRequest);
}
