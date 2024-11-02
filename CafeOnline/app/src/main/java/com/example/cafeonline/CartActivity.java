package com.example.cafeonline;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import com.example.cafeonline.adapter.AddressAdapter;
import com.example.cafeonline.adapter.CartAdapter;
import com.example.cafeonline.api.ApiService;
import com.example.cafeonline.api.CartApiService;
import com.example.cafeonline.api.UserApiService;
import com.example.cafeonline.model.request.CartItemRequestModel;
import com.example.cafeonline.model.response.AddressResponse;
import com.example.cafeonline.model.response.ApiResponse;
import com.example.cafeonline.model.response.CartItemResponse;
import com.example.cafeonline.model.response.CartResponse;
import com.example.cafeonline.service.NotificationService;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    private Button orderButton;
    private RecyclerView recyclerView;
    private TextView tvTotalPrice, tvAddress;
    private ImageView imgBack;
    private LinearLayout addOtherDrinks;
    private RelativeLayout address, payment;
    private CartAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        imgBack = findViewById(R.id.img_toolbar_back);
        imgBack.setOnClickListener(v -> onBackPressed());
        addOtherDrinks = findViewById(R.id.layout_add_other_drink);
        addOtherDrinks.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        payment = findViewById(R.id.layout_payment_method);
        payment.setOnClickListener(v -> {
            Intent intent = new Intent(this, PaymentMethodActivity.class);
            startActivity(intent);
        });
        address = findViewById(R.id.layout_address);
        address.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddressActivity.class);
            startActivity(intent);
        });
        AddressResponse addressResponse = getAddressFromPreferences();
        tvAddress = findViewById(R.id.tv_address);
tvAddress.setText(addressResponse.getAddress().toString());
        orderButton = findViewById(R.id.btn_checkout);
        recyclerView = findViewById(R.id.rcv_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvTotalPrice = findViewById(R.id.tv_amount);
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
                            DecimalFormat decimalFormat = new DecimalFormat("#,###");
                            String formattedPrice = decimalFormat.format(cart.getTotalPrice());
                            tvTotalPrice.setText(formattedPrice+ " VND");
                            List<CartItemResponse> cartItems = cart.getCartItems();
                            if (cartItems != null && !cartItems.isEmpty()) {
                                setupAdapter(cartItems);
                            } else {
                                Toast.makeText(CartActivity.this, "No items to view", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CartActivity.this, "Your cart is empty", Toast.LENGTH_SHORT).show();
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

    private void setupAdapter(List<CartItemResponse> cartItemResponses) {
        adapter = new CartAdapter(cartItemResponses, new CartAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(CartItemResponse cartItemResponse) {
                Toast.makeText(CartActivity.this, "Load cart successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(CartItemResponse cartItemResponse) {
                deleteCartItem(cartItemResponse);
            }
        }, CartActivity.this);
        recyclerView.setAdapter(adapter);
        // Gọi Service để hiển thị notification
        Intent serviceIntent = new Intent(this, NotificationService.class);
        serviceIntent.putExtra("title", "KooHee");
        serviceIntent.putExtra("text", "The number of items in your cart: " + adapter.getTotalQuantity());
        startService(serviceIntent);
    }

    public void updateCartItem(CartItemResponse cartItemResponse) {
        CartItemRequestModel requestModel = new CartItemRequestModel();
        requestModel.setNote(cartItemResponse.getNote());
        requestModel.setVariant(cartItemResponse.getVariant());
        requestModel.setSugar(cartItemResponse.getSugar());
        requestModel.setSize(cartItemResponse.getSize());
        requestModel.setIced(cartItemResponse.getIced());
        requestModel.setQuantity(cartItemResponse.getQuantity());
        requestModel.setTotalPrice(cartItemResponse.getTotalPrice());// Extract cart item ID
        CartApiService cartService = ApiService.createService(CartApiService.class);

        Call<ApiResponse<String>> call = cartService.updateCartItem(cartItemResponse.getId(), requestModel);
        call.enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.isSuccessful()) {
                    // Xử lý thành công
                    ApiResponse<String> apiResponse = response.body();
                    if ("200".equals(apiResponse.getValue().getStatus())) {

                        Toast.makeText(CartActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void deleteCartItem(CartItemResponse cartItemResponse) {
        int id = cartItemResponse.getId();
        CartApiService service = ApiService.createService(CartApiService.class);
        Call<ApiResponse<String>> callApiDrink = service.deleteCartItems(id);
        callApiDrink.enqueue(new Callback<ApiResponse<String>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<String> apiResponse = response.body();
                    if ("200".equals(apiResponse.getValue().getStatus())) {
                        adapter.deleteCartItem(cartItemResponse);
                        adapter.notifyDataSetChanged();

                        Toast.makeText(CartActivity.this, "Cart item deleted successfully", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(CartActivity.this, "Error fetching caritem", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        if (response.errorBody() != null) {
                            Gson gson = new Gson();
                            ApiResponse<String> errorResponse = gson.fromJson(response.errorBody().string(), ApiResponse.class);
                            System.out.println(errorResponse.getValue().getMessage());
                            Toast.makeText(CartActivity.this, "Error fetching addresses", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateTotalPrice(double totalPrice) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedPrice = decimalFormat.format(totalPrice);
        tvTotalPrice.setText(formattedPrice + " VND");
    }

    private int getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("KooheePrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("userId", 0); // Returns null if no userId is found
    }

    private AddressResponse getAddressFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("KooheePrefs", MODE_PRIVATE);
        int addressId = sharedPreferences.getInt("addressId",-1);
        String fullname = sharedPreferences.getString("fullName",null);
        String phone = sharedPreferences.getString("phone",null);
        String address = sharedPreferences.getString("address",null);
        AddressResponse addressResponse = new AddressResponse(addressId, 0, fullname, phone, address);
        return addressResponse;
    }
}




