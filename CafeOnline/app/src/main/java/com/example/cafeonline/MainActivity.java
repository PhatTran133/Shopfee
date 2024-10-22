package com.example.cafeonline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.cafeonline.service.NotificationService;
import com.example.cafeonline.adapter.DrinkAdapter;
import com.example.cafeonline.api.DrinkApiService;
import com.example.cafeonline.model.response.DrinkResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.cafeonline.api.ApiService;
import com.example.cafeonline.model.response.ApiResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int userId = getUserIdFromPreferences();
        recyclerView = findViewById(R.id.rcv_drink);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


//    ĐỪNG XÓA COMMENT NÀY
//        Intent intent = new Intent(MainActivity.this, DrinkDetailActivity.class);
//        startActivity(intent);

        //region Bottom Nav
                BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_history) {

                //Thêm if else để check đăng nhập chưa
                //Nếu chưa thì sang screen login
                if (userId == 0)
                {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return true;
                }
                //Rồi thì sang screen đơn hàng
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_account) {

                //Thêm if else để check đăng nhập chưa
                //Nếu chưa thì sang screen login
                if (userId == 0)
                {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return true;
                }
                //Nếu rồi thì sang screen account
                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
        //endregion

        //kiếm id của image slider
        ImageSlider imageSlider = findViewById(R.id.imageSlider);
        //tạo array list cho ảnh trong slider
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        //Add ảnh vào slider

        slideModels.add(new SlideModel(R.drawable.drink_example, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.ic_logo, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.ic_splash, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.drink_example, ScaleTypes.FIT));


        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        // Gọi Service để hiển thị notification
        Intent serviceIntent = new Intent(this, NotificationService.class);
        startService(serviceIntent);

        DrinkApiService drinkService = ApiService.createService(DrinkApiService.class);
        Call<ApiResponse<List<DrinkResponse>>> callApiDrink = drinkService.getDrinkFilter();
        callApiDrink.enqueue(new Callback<ApiResponse<List<DrinkResponse>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<DrinkResponse>>> callApiDrink, Response<ApiResponse<List<DrinkResponse>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<DrinkResponse>> apiResponse = response.body();
                    if ("200".equals(apiResponse.getValue().getStatus())) {
                        List<DrinkResponse> drink = apiResponse.getValue().getData();
                        DrinkAdapter adapter = new DrinkAdapter(drink);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(MainActivity.this, apiResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        // Check if error body exists
                        if (response.errorBody() != null) {
                            // Deserialize error body to ApiResponse<String>
                            Gson gson = new Gson();
                            ApiResponse<String> errorResponse = gson.fromJson(response.errorBody().string(), ApiResponse.class);
                            System.out.println(errorResponse.getValue().getMessage());
                            Toast.makeText(MainActivity.this, "Fetch Failed: " + errorResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Error parsing response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<DrinkResponse>>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });



    }
//    private void loadProducts() {
//        // Gọi API getAllProducts
//        // ...
//s
//        // Tạo Intent để chuyển sang màn hình danh sách sản phẩm
//        Intent intent = new Intent(this, DrinkFilterActivity.class);
//        intent.putExtra("products", products); // Truyền danh sách sản phẩm
//        startActivity(intent);
//    }

    private int getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("KooheePrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("userId", 0); // Returns null if no userId is found
    }

}
