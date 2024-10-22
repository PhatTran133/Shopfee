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

import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button btnSearch;
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
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        // Gọi Service để hiển thị notification
        Intent serviceIntent = new Intent(this, NotificationService.class);
        serviceIntent.putExtra("title", "Welcome to Koohee"); // Truyền title
        serviceIntent.putExtra("text", "Lào gì cũng tôn" + userId); // Truyền text
        startService(serviceIntent);
    }

    private int getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("KooheePrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("userId", 0); // Returns null if no userId is found
    }

}