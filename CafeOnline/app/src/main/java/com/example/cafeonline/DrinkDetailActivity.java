package com.example.cafeonline;

import android.content.Intent;
import android.icu.math.BigDecimal;
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
    private TextView tvName, tvDescription, tvPrice, tvSub, tvAdd, tvCount;
    private int count = 1;
    private String price = null;
    private TextView selectedVariant = null;
    private TextView selectedSize = null;
    private TextView selectedSugar = null;
    private TextView selectedIce = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_detail);

        tvName = findViewById(R.id.tv_name);
        tvDescription = findViewById(R.id.tv_description);
        tvPrice = findViewById(R.id.tv_price_sale);

        tvSub = findViewById(R.id.tv_sub);
        tvCount = findViewById(R.id.tv_count);
        tvAdd = findViewById(R.id.tv_add);

        imgBack = findViewById(R.id.img_toolbar_back);
        imgBack.setOnClickListener(v -> onBackPressed());

        tvSub.setOnClickListener(v -> {
            if (count > 1) { // Giảm count nhưng không cho phép nhỏ hơn 1
                count--;
                tvCount.setText(String.valueOf(count));
                double doublePrice = Double.parseDouble(price) * count;
                tvPrice.setText(String.valueOf(doublePrice));
            }
        });
        tvAdd.setOnClickListener(v -> {
            count++; // Tăng count
            tvCount.setText(String.valueOf(count));
            double doublePrice = Double.parseDouble(price) * count;
            tvPrice.setText(String.valueOf(doublePrice));
        });

        TextView tvVariantIce = findViewById(R.id.tv_variant_ice);
        TextView tvVariantHot = findViewById(R.id.tv_variant_hot);


        tvVariantIce.setOnClickListener(v -> selectOption(tvVariantIce, "variant"));
        tvVariantHot.setOnClickListener(v -> selectOption(tvVariantHot, "variant"));

        TextView tvSizeSmall = findViewById(R.id.tv_size_small);
        TextView tvSizeMedium = findViewById(R.id.tv_size_medium);
        TextView tvSizeLarge = findViewById(R.id.tv_size_large);

        tvSizeSmall.setOnClickListener(v -> selectOption(tvSizeSmall, "size"));
        tvSizeMedium.setOnClickListener(v -> selectOption(tvSizeMedium, "size"));
        tvSizeLarge.setOnClickListener(v -> selectOption(tvSizeLarge, "size"));

        TextView tvSugarNormal = findViewById(R.id.tv_sugar_normal);
        TextView tvSugarLess = findViewById(R.id.tv_sugar_less);

        tvSugarNormal.setOnClickListener(v -> selectOption(tvSugarNormal, "sugar"));
        tvSugarLess.setOnClickListener(v -> selectOption(tvSugarLess, "sugar"));

        TextView tvIceNormal = findViewById(R.id.tv_ice_normal);
        TextView tvIceLess = findViewById(R.id.tv_ice_less);

        tvIceNormal.setOnClickListener(v -> selectOption(tvIceNormal, "ice"));
        tvIceLess.setOnClickListener(v -> selectOption(tvIceLess, "ice"));
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
    private void selectOption(TextView selectedTextView, String type) {
        TextView previousSelection = null;

        switch (type) {
            case "variant":
                previousSelection = selectedVariant;
                selectedVariant = selectedTextView;
                break;
            case "size":
                previousSelection = selectedSize;
                selectedSize = selectedTextView;
                break;
            case "sugar":
                previousSelection = selectedSugar;
                selectedSugar = selectedTextView;
                break;
            case "ice":
                previousSelection = selectedIce;
                selectedIce = selectedTextView;
                break;
        }

        // Đổi màu ô cũ về trạng thái bình thường nếu có
        if (previousSelection != null) {
            previousSelection.setBackgroundResource(R.drawable.bg_white_corner_6_border_main);
            previousSelection.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        // Tô đen ô hiện tại
        selectedTextView.setBackgroundResource(R.drawable.bg_main_corner_6);
        selectedTextView.setTextColor(getResources().getColor(R.color.bgMainColor));
    }

}
