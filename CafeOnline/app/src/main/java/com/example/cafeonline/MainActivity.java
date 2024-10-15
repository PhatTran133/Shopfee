package com.example.cafeonline;

import android.content.Intent;
import android.os.Bundle;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


import com.example.cafeonline.api.ApiService;
import com.example.cafeonline.api.UserApiService;
import com.example.cafeonline.model.request.LoginRequest;
import com.example.cafeonline.model.response.ApiResponse;
import com.example.cafeonline.model.response.UserResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

//    ĐỪNG XÓA COMMENT NÀY
        Intent intent = new Intent(MainActivity.this, DrinkDetailActivity.class);
        startActivity(intent);

//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
//            if (item.getItemId() == R.id.nav_home) {
//                Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                startActivity(intent);
//                return true;
//            } else if (item.getItemId() == R.id.nav_history) {
//
//                //Thêm if else để check đăng nhập chưa
//                //Nếu chưa thì sang screen login
//                //Rồi thì sang screen đơn hàng
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
//                return true;
//            } else if (item.getItemId() == R.id.nav_account) {
//
//                //Thêm if else để check đăng nhập chưa
//                //Nếu chưa thì sang screen login
//
////                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
////                startActivity(intent);
////                return true;
//
//                //Nếu rồi thì sang screen
//                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
//                startActivity(intent);
//                return true;
//            }
//            return false;
//        });

        //kiếm id của image slider
        ImageSlider imageSlider = findViewById(R.id.imageSlider);
        //tạo array list cho ảnh trong slider
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        //Add ảnh vào slider
        slideModels.add(new SlideModel(R.drawable.baseline_account_circle_24, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.baseline_account_circle_24, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.baseline_account_circle_24, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.baseline_account_circle_24, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
    }
}
