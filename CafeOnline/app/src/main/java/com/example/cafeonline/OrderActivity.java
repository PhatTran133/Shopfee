package com.example.cafeonline;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cafeonline.adapter.CartAdapter;
import com.example.cafeonline.adapter.OrderAdapter;
import com.example.cafeonline.api.ApiService;
import com.example.cafeonline.api.OrderApiService;
import com.example.cafeonline.model.response.AddressResponse;
import com.example.cafeonline.model.response.ApiResponse;
import com.example.cafeonline.model.response.CartItemResponse;
import com.example.cafeonline.model.response.DrinkResponse;
import com.example.cafeonline.model.response.OrderItemResponse;
import com.example.cafeonline.model.response.OrderResponse;
import com.example.cafeonline.model.response.PaymentResponse;
import com.example.cafeonline.service.NotificationService;
import com.example.cafeonline.ui.HomeFragment;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    private ImageView imgBack;
    private TextView tvTransaction, tvDate, tvPrice, tvTotal, tvPaymentMethod,tvFullName,tvPhone,tvAddress;
    private Button btnTrackingOrder;
    private OrderAdapter adapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_order);
        imgBack = findViewById(R.id.img_toolbar_back);
        imgBack.setOnClickListener(v -> onBackPressed());
        btnTrackingOrder = findViewById(R.id.btn_tracking_order);
        btnTrackingOrder.setOnClickListener(v -> {
            Intent intent = new Intent(this, TrackingOrderActivity.class);
            startActivity(intent);
            finish();
        });
        recyclerView = findViewById(R.id.rcv_drinks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvTransaction = findViewById(R.id.tv_id_transaction);
        tvDate = findViewById(R.id.tv_date_time);
        tvPrice = findViewById(R.id.tv_price);
        tvTotal =findViewById(R.id.tv_total);
        tvFullName = findViewById(R.id.tv_name);
        tvPhone =findViewById(R.id.tv_phone);
        tvAddress= findViewById(R.id.tv_address);
        tvPaymentMethod = findViewById(R.id.tv_payment_method);
        getOrderData();
        }
        private void getOrderData(){
            int orderId = getOrderIdFromPreferences();
            if (orderId == 0){
                Intent intent = new Intent(OrderActivity.this, LoginActivity.class);
                startActivity(intent);
                return;
            }
            OrderApiService orderApiService = ApiService.createService(OrderApiService.class);
            Call<ApiResponse<OrderResponse>> call = orderApiService.getOrderById(orderId);

            call.enqueue(new Callback<ApiResponse<OrderResponse>>() {

                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<ApiResponse<OrderResponse>> call, Response<ApiResponse<OrderResponse>> response) {
                    if (response.isSuccessful()) {
                        ApiResponse<OrderResponse> apiResponse = response.body();
                        if ("200".equals(apiResponse.getValue().getStatus())) {
                            OrderResponse order = apiResponse.getValue().getData();
                            Date createdDate = order.getCreatedDate();
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            String formattedDate = formatter.format(createdDate);
                            tvDate.setText(formattedDate);
                            List<PaymentResponse> paymentResponses = order.getPaymentDTOs();
                            for (PaymentResponse payment : paymentResponses) {
                                tvPaymentMethod.setText(payment.getType());
                            }
                            DecimalFormat decimalFormat = new DecimalFormat("#,###");
                            String formattedPrice = decimalFormat.format(order.getTotal());
                            tvPrice.setText(formattedPrice + " VND");
                            tvTotal.setText(formattedPrice + " VND");
                            AddressResponse addressResponse = getAddressFromPreferences();
                            tvFullName.setText(addressResponse.getName());
                            tvPhone.setText(addressResponse.getPhone());
                            tvAddress.setText(addressResponse.getAddress());
                            List<OrderItemResponse> orderItemResponse = order.getOrderItemDTOs();
                            if(orderItemResponse != null && !orderItemResponse.isEmpty()){
                                adapter = new OrderAdapter(orderItemResponse);
                                recyclerView.setAdapter(adapter);
                            }else {
                                Toast.makeText(OrderActivity.this, "There nothing to show", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(OrderActivity.this, apiResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        try {
                            // Check if error body exists
                            if (response.errorBody() != null) {
                                // Deserialize error body to ApiResponse<String>
                                Gson gson = new Gson();
                                ApiResponse<String> errorResponse = gson.fromJson(response.errorBody().string(), ApiResponse.class);
                                System.out.println(errorResponse.getValue().getMessage());
                                Toast.makeText(OrderActivity.this, "Fetch Failed: " + errorResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(OrderActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(OrderActivity.this, "Error parsing response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            System.out.println(e.getMessage());
                        }
                    }
                }


                @Override
                public void onFailure(Call<ApiResponse<OrderResponse>> call,  Throwable t) {
                    // Handle network or other errors
                }
            });

        }

    private int getOrderIdFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("KooheePrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("orderId", 0); // Returns null if no userId is found
    }
    private AddressResponse getAddressFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("KooheePrefs", MODE_PRIVATE);
        int addressId = sharedPreferences.getInt("addressId", -1);
        String fullname = sharedPreferences.getString("fullName", null);
        String phone = sharedPreferences.getString("phone", null);
        String address = sharedPreferences.getString("address", null);
        AddressResponse addressResponse = new AddressResponse(addressId, 0, fullname, phone, address);
        return addressResponse;
    }
    }

