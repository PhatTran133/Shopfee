package com.example.cafeonline;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cafeonline.api.ApiService;
import com.example.cafeonline.api.DrinkApiService;
import com.example.cafeonline.api.UserApiService;
import com.example.cafeonline.model.response.ApiResponse;
import com.example.cafeonline.model.response.DrinkResponse;
import com.example.cafeonline.model.response.UserResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrinkDetailActivity extends AppCompatActivity {
    private ImageView imgBack;
    private TextView tvName, tvDescription, tvPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_detail);

        tvName = findViewById(R.id.tv_name);
        tvDescription = findViewById(R.id.tv_description);
        tvPrice = findViewById(R.id.tv_price_sale);

        imgBack = findViewById(R.id.img_toolbar_back);
        imgBack.setOnClickListener(v -> onBackPressed());

        //CHỈNH LẠI DRINK ID ĐƯỢC INTENT TRUYỀN VÀO
        int drinkId = 1;
        DrinkApiService drinkService = ApiService.createService(DrinkApiService.class);
        Call<ApiResponse<DrinkResponse>> call = drinkService.getDrinkDetail(drinkId);
        call.enqueue(new Callback<ApiResponse<DrinkResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<DrinkResponse>> call, Response<ApiResponse<DrinkResponse>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<DrinkResponse> apiResponse = response.body();
                    if ("200".equals(apiResponse.getValue().getStatus())) {
                        DrinkResponse drink = apiResponse.getValue().getData();
                        tvName.setText(drink.getName());
                        tvDescription.setText(drink.getDescription());
                        tvPrice.setText(drink.getPrice().toString());
                    } else {
                        Toast.makeText(DrinkDetailActivity.this, apiResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        // Check if error body exists
                        if (response.errorBody() != null) {
                            // Deserialize error body to ApiResponse<String>
                            Gson gson = new Gson();
                            ApiResponse<String> errorResponse = gson.fromJson(response.errorBody().string(), ApiResponse.class);
                            System.out.println(errorResponse.getValue().getMessage());
                            Toast.makeText(DrinkDetailActivity.this, "Fetch Failed: " + errorResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DrinkDetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(DrinkDetailActivity.this, "Error parsing response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<DrinkResponse>> call, Throwable t) {
                Toast.makeText(DrinkDetailActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
