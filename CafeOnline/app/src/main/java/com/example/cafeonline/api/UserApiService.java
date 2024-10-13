package com.example.cafeonline.api;

import com.example.cafeonline.model.request.ForgotPasswordRequest;
import com.example.cafeonline.model.request.LoginRequest;
import com.example.cafeonline.model.request.RegisterRequest;
import com.example.cafeonline.model.request.ResetPasswordRequest;
import com.example.cafeonline.model.request.UserProfileRequest;
import com.example.cafeonline.model.request.VerifyCodeRequest;
import com.example.cafeonline.model.response.ApiResponse;
import com.example.cafeonline.model.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiService {

    //PHAT
    @POST("api/users/login")
    Call<ApiResponse<UserResponse>> loginUser(@Body LoginRequest loginRequest);
    @POST("api/users/register")
    Call<ApiResponse<String>> registerUser(@Body RegisterRequest registerRequest);
    @POST("")//Check lại theo BE
    Call<ApiResponse<String>> verifyCodeForRegister(@Body VerifyCodeRequest verifyCodeRequest);


    //DƯƠNG
    @POST("/api/authentication/forgot-password")
    Call<ApiResponse<String>> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);
    @POST("api/authentication/verify-reset-code")
    Call<ApiResponse<String>> verifyCode(@Body VerifyCodeRequest verifyCodeRequest);
    @POST("api/authentication/reset-password")
    Call<ApiResponse<ResetPasswordRequest>> resetPassword(@Body ResetPasswordRequest resetPasswordRequest);
    @POST("/api/users/{userId}")
    Call<ApiResponse<UserResponse>> userProfile(@Body int Id);

}
