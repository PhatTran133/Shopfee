package com.example.cafeonline.api;

import com.example.cafeonline.model.request.PaymentRequest;
import com.example.cafeonline.model.response.ApiResponse;
import com.example.cafeonline.model.response.PaymentUrlResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PaymentApiService {

    @POST("/api/Payment/CreatePaymentUrl")
    Call<ApiResponse<PaymentUrlResponse>> createPaymentUrl(@Body PaymentRequest paymentRequest);
}
