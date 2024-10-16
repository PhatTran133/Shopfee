package com.example.cafeonline;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cafeonline.api.ApiService;
import com.example.cafeonline.api.UserApiService;
import com.example.cafeonline.model.request.ForgotPasswordRequest;
import com.example.cafeonline.model.request.UserProfileRequest;
import com.example.cafeonline.model.response.ApiResponse;
import com.example.cafeonline.model.response.UserResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {
    private EditText Name, Number, Address;
    private UserResponse user;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        Name = findViewById(R.id.update_username);
        Number = findViewById(R.id.update_phone);
        Address = findViewById(R.id.update_address);
        imgBack = findViewById(R.id.img_toolbar_back);

        imgBack.setOnClickListener(v -> onBackPressed());
//        int userId = getIntent().getIntExtra("userId", 0);
        int userId = getUserIdFromPreferences();
        UserApiService authService = ApiService.createService(UserApiService.class);

        // Make API call
        Call<ApiResponse<UserResponse>> call = authService.userProfile(userId);
        call.enqueue(new Callback<ApiResponse<UserResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserResponse>> call, Response<ApiResponse<UserResponse>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<UserResponse> apiResponse = response.body();
                    if ("200".equals(apiResponse.getValue().getStatus())) {
                        UserResponse user = apiResponse.getValue().getData();
                        Name.setText(user.getUsername());
                        Number.setText(user.getPhone());
                        Address.setText(user.getAddress());
                    } else {
                        Toast.makeText(UserProfileActivity.this, apiResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        // Check if error body exists
                        if (response.errorBody() != null) {
                            // Deserialize error body to ApiResponse<String>
                            Gson gson = new Gson();
                            ApiResponse<String> errorResponse = gson.fromJson(response.errorBody().string(), ApiResponse.class);
                            System.out.println(errorResponse.getValue().getMessage());
                            Toast.makeText(UserProfileActivity.this, "Fetch Failed: " + errorResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UserProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(UserProfileActivity.this, "Error parsing response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<UserResponse>> call, Throwable t) {
                Toast.makeText(UserProfileActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private int getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("KooheePrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("userId", 0); // Returns null if no userId is found
    }
}



