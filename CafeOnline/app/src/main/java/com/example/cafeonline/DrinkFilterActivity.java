package com.example.cafeonline;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cafeonline.api.ApiService;
import com.example.cafeonline.api.DrinkApiService;
import com.example.cafeonline.model.request.DrinkRequestModel;
import com.example.cafeonline.model.response.ApiResponse;
import com.example.cafeonline.model.response.DrinkResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;

public class DrinkFilterActivity extends AppCompatActivity {


    DrinkRequestModel request = new DrinkRequestModel();
    DrinkApiService drinkService = ApiService.createService(DrinkApiService.class);
    Call<ApiResponse<DrinkResponse>> call = drinkService.getDrinkFilter(request);
        call.enqueue(new Callback<ApiResponse<DrinkResponse>>() {
        @Override
        public void onResponse(Call<ApiResponse<DrinkResponse>> call, Response<ApiResponse<DrinkResponse>> response) {
            if (response.isSuccessful()) {
                ApiResponse<DrinkResponse> apiResponse = response.body();
                if ("200".equals(apiResponse.getValue().getStatus())) {
                    DrinkResponse drink = apiResponse.getValue().getData();
                    tvName.setText(drink.getName());
                    tvDescription.setText(drink.getDescription());
                    price = drink.getPrice().toString();
                    tvPrice.setText(price);
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
