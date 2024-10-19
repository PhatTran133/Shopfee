package com.example.cafeonline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.example.cafeonline.ui.HomeFragment;
import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.cafeonline.service.NotificationService;
import com.example.cafeonline.adapter.DrinkAdapter;
import com.example.cafeonline.api.DrinkApiService;
import com.example.cafeonline.model.request.DrinkRequestModel;
import com.example.cafeonline.model.response.DrinkResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import com.example.cafeonline.api.ApiService;
import com.example.cafeonline.api.UserApiService;
import com.example.cafeonline.model.request.LoginRequest;
import com.example.cafeonline.model.response.ApiResponse;
import com.example.cafeonline.model.response.UserResponse;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText search;
    private String name, categoryName, size;
    private double minPrice,maxPrice;
    private Date startDate,endDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }
        int userId = getUserIdFromPreferences();
        search = findViewById(R.id.edt_search_name);

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

        slideModels.add(new SlideModel(R.drawable.drink_example, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.ic_logo, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.ic_splash, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.drink_example, ScaleTypes.FIT));


        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
        name= search.getText().toString();
        categoryName=search.getText().toString();
        size=search.getText().toString();
        DrinkApiService drinkService = ApiService.createService(DrinkApiService.class);
        Call<ApiResponse<List<DrinkResponse>>> callApiDrink = drinkService.getDrinkFilter(name);
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
                            ApiResponse errorResponse = gson.fromJson(response.errorBody().string(), ApiResponse.class);
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
        }

        // Gọi Service để hiển thị notification
        Intent serviceIntent = new Intent(this, NotificationService.class);
        startService(serviceIntent);
    }

    private int getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("KooheePrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("userId", 0); // Returns null if no userId is found
    }

}
