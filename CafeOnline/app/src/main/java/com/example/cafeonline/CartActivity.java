package com.example.cafeonline;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeonline.adapter.CartAdapter;
import com.example.cafeonline.api.ApiService;
import com.example.cafeonline.api.CartApiService;
import com.example.cafeonline.model.response.ApiResponse;
import com.example.cafeonline.model.response.CartItemResponse;
import com.example.cafeonline.model.response.CartResponse;
import com.example.cafeonline.service.NotificationService;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    private Button orderButton;
    private RecyclerView recyclerView;
    private TextView tvName, tvOption, tvPrice, tvQuantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        orderButton = findViewById(R.id.btn_checkout);
        recyclerView = findViewById(R.id.rcv_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvName = findViewById(R.id.tv_drink);
        tvOption = findViewById(R.id.tv_option);
        tvPrice = findViewById(R.id.tv_price);
        orderButton.setOnClickListener(v -> {
            // Gọi Service để hiển thị notification
            Intent serviceIntent = new Intent(this, NotificationService.class);
            serviceIntent.putExtra("title", "Order");
            serviceIntent.putExtra("text", "Your order is pending");
            startService(serviceIntent);
        });


            int userId = getUserIdFromPreferences();
            CartApiService cartService = ApiService.createService(CartApiService.class);
            Call<ApiResponse<CartResponse>> callApiDrink = cartService.getCart(userId);
            callApiDrink.enqueue(new Callback<ApiResponse<CartResponse>>() {
                @Override
                public void onResponse(Call<ApiResponse<CartResponse>> callApiDrink, Response<ApiResponse<CartResponse>> response) {
                    if (response.isSuccessful()) {
                        ApiResponse<CartResponse> apiResponse = response.body();
                        if ("200".equals(apiResponse.getValue().getStatus())) {
                            CartResponse cart = apiResponse.getValue().getData();

                            if (cart != null) {
                                List<CartItemResponse> cartItems = cart.getCartItems();

                                if (cartItems != null && !cartItems.isEmpty()) {
                                    // Cập nhật dữ liệu cho Adapter
                                 CartAdapter  cartAdapter = new CartAdapter(cartItems,null);
                                 recyclerView.setAdapter(cartAdapter);
                                } else {
                                    // Hiển thị thông báo giỏ hàng rỗng
                                    Toast.makeText(CartActivity.this, "Giỏ hàng của bạn đang trống", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Xử lý trường hợp CartResponse là null
                                Toast.makeText(CartActivity.this, "Có lỗi xảy ra khi lấy dữ liệu giỏ hàng", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } else {
                        try {
                            // Check if error body exists
                            if (response.errorBody() != null) {
                                // Deserialize error body to ApiResponse<String>
                                Gson gson = new Gson();
                                ApiResponse<String> errorResponse = gson.fromJson(response.errorBody().string(), ApiResponse.class);
                                System.out.println(errorResponse.getValue().getMessage());
                                Toast.makeText(CartActivity.this, "Fetch Failed: " + errorResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CartActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(CartActivity.this, "Error parsing response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            System.out.println(e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<CartResponse>> call, Throwable t) {
                    Toast.makeText(CartActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                }
            });

    }
    private int getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("KooheePrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("userId", 0); // Returns null if no userId is found
    }

        }


