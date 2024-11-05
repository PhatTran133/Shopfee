package com.example.cafeonline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cafeonline.api.ApiService;
import com.example.cafeonline.api.UserApiService;
import com.example.cafeonline.model.request.AddressRequest;
import com.example.cafeonline.model.response.AddressResponse;
import com.example.cafeonline.model.response.ApiResponse;
import com.example.cafeonline.model.response.UserResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressActivicty extends AppCompatActivity {
    private ImageView imgBack;
    private Button btnAdd;
    private TextView tvName, tvPhone, tvAdress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        imgBack = findViewById(R.id.img_toolbar_back);
        imgBack.setOnClickListener(v -> onBackPressed());
        tvName = findViewById(R.id.add_full_name);
        tvPhone = findViewById(R.id.add_phone);
        tvAdress = findViewById(R.id.add_address);
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(v -> getAddress());


    }
        public void getAddress(){
            int userId = getUserIdFromPreferences();
            String name = tvName.getText().toString().trim();
            String phone = tvPhone.getText().toString().trim();
            String address = tvAdress.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (phone.isEmpty()) {
                Toast.makeText(this, "Please enter phone", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (address.isEmpty()) {
                Toast.makeText(this, "Please enter address", Toast.LENGTH_SHORT).show();
                return;
            }
            UserApiService authService = ApiService.createService(UserApiService.class);
            AddressRequest addressRequest = new AddressRequest(name, phone, address);
            // Make API call
            Call<ApiResponse<AddressResponse>> call = authService.addAddress(userId,addressRequest);
            call.enqueue(new Callback<ApiResponse<AddressResponse>>(){
                @Override
                public void onResponse(Call<ApiResponse<AddressResponse>> call, Response<ApiResponse<AddressResponse>> response){
                    if(response.isSuccessful() && response.body() != null){
                        ApiResponse<AddressResponse> apiResponse = response.body();

                        if("200".equals(apiResponse.getValue().getStatus())){
                            Toast.makeText(AddAddressActivicty.this,"Add Address Successfully" +  apiResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddAddressActivicty.this, AddressActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(AddAddressActivicty.this, "Add Address Failed" + apiResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        try {
                            // Check if error body exists
                            if (response.errorBody() != null) {
                                // Deserialize error body to ApiResponse<String>
                                Gson gson = new Gson();
                                ApiResponse<String> errorResponse = gson.fromJson(response.errorBody().string(), ApiResponse.class);
                                Toast.makeText(AddAddressActivicty.this, "Add Address Failed: " + errorResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddAddressActivicty.this, "Error: Invalid response", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(AddAddressActivicty.this, "Error parsing response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                @Override
                public void onFailure(Call<ApiResponse<AddressResponse>> call, Throwable t){
                    Toast.makeText(AddAddressActivicty.this, "Add Address Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }

            });
        }



    private int getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("KooheePrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("userId", 0); // Returns null if no userId is found
    }

}

