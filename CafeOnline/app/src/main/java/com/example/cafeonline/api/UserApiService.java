package com.example.cafeonline.api;

import com.example.cafeonline.model.request.AddressRequest;
import com.example.cafeonline.model.request.ChangePasswordRequestModel;
import com.example.cafeonline.model.request.ForgotPasswordRequest;
import com.example.cafeonline.model.request.LoginRequest;
import com.example.cafeonline.model.request.RegisterRequest;
import com.example.cafeonline.model.request.ResetPasswordRequest;
import com.example.cafeonline.model.request.UserProfileRequest;
import com.example.cafeonline.model.request.VerifyCodeRequest;
import com.example.cafeonline.model.response.AddressResponse;
import com.example.cafeonline.model.response.ApiResponse;
import com.example.cafeonline.model.response.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApiService {

    //PHAT
    @POST("api/users/login")
    Call<ApiResponse<UserResponse>> loginUser(@Body LoginRequest loginRequest);
    @POST("api/users/register")
    Call<ApiResponse<String>> registerUser(@Body RegisterRequest registerRequest);
    @POST("api/Otp/verify-otp-register")//Check lại theo BE
    Call<ApiResponse<Integer>> verifyCodeForRegister(@Body VerifyCodeRequest verifyCodeRequest);


    //DƯƠNG
    @POST("/api/authentication/forgot-password")
    Call<ApiResponse<String>> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);
    @POST("api/authentication/verify-reset-code")
    Call<ApiResponse<String>> verifyCode(@Body VerifyCodeRequest verifyCodeRequest);
    @PUT("api/authentication/reset-password")
    Call<ApiResponse<ResetPasswordRequest>> resetPassword(@Body ResetPasswordRequest resetPasswordRequest);
    @GET("/api/users/{userId}")
    Call<ApiResponse<UserResponse>> userProfile(@Path("userId") int userId);
    @POST("/api/users/add-address/{userId}")
    Call<ApiResponse<AddressResponse>> addAddress(@Path("userId")int userId, @Body AddressRequest addressRequest);
    @GET("/api/users/get-all-addresses/{userId}")
    Call<ApiResponse<List<AddressResponse>>> getAllAddresses(@Path("userId") int userId);
    @DELETE("/api/users/delete-address/{userId}/{addressId}")
    Call<ApiResponse<String>> deleteAddress(@Path("userId") int userId, @Path("addressId") int addressId);
    @POST("/api/users/change-password")
    Call<ApiResponse<String>> changePassword(@Body ChangePasswordRequestModel changePasswordRequestModel);
}
