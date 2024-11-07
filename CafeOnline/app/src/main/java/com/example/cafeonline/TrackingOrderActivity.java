package com.example.cafeonline;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeonline.adapter.OrderAdapter;
import com.example.cafeonline.adapter.OrderReceiptAdapter;
import com.example.cafeonline.api.ApiService;
import com.example.cafeonline.api.OrderApiService;
import com.example.cafeonline.model.response.AddressResponse;
import com.example.cafeonline.model.response.ApiResponse;
import com.example.cafeonline.model.response.OrderItemResponse;
import com.example.cafeonline.model.response.OrderResponse;
import com.example.cafeonline.model.response.PaymentResponse;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackingOrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderReceiptAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_order);

        recyclerView = findViewById(R.id.rcv_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchOrders();
    }

    private void fetchOrders() {
       int userId = getUserIdFromPreferences();
        String orderStatus = "SUCCESS";

        OrderApiService orderApiService = ApiService.createService(OrderApiService.class);
        Call<ApiResponse<List<OrderResponse>>> call = orderApiService.getOrder(userId, orderStatus);

        call.enqueue(new Callback<ApiResponse<List<OrderResponse>>>() {

            @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
            @Override
            public void onResponse(Call<ApiResponse<List<OrderResponse>>> call, Response<ApiResponse<List<OrderResponse>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<OrderResponse>> apiResponse = response.body();
                    if ("200".equals(apiResponse.getValue().getStatus())) {
                        List<OrderResponse> orders = apiResponse.getValue().getData(); // Đây là danh sách OrderResponse
                        orderAdapter = new OrderReceiptAdapter(orders);
                        recyclerView.setAdapter(orderAdapter);

                    } else {
                        Toast.makeText(TrackingOrderActivity.this, apiResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        // Check if error body exists
                        if (response.errorBody() != null) {
                            // Deserialize error body to ApiResponse<String>
                            Gson gson = new Gson();
                            ApiResponse<String> errorResponse = gson.fromJson(response.errorBody().string(), ApiResponse.class);
                            System.out.println(errorResponse.getValue().getMessage());
                            Toast.makeText(TrackingOrderActivity.this, "Fetch Failed: " + errorResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(TrackingOrderActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(TrackingOrderActivity.this, "Error parsing response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println(e.getMessage());
                    }
                }
            }


            @Override
            public void onFailure(Call<ApiResponse<List<OrderResponse>>> call,  Throwable t) {
                Toast.makeText(TrackingOrderActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private int getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("KooheePrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("userId", 0); // Returns null if no userId is found
    }
}
