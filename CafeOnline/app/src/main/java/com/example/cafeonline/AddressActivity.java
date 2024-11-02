package com.example.cafeonline;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeonline.adapter.AddressAdapter;
import com.example.cafeonline.adapter.DrinkAdapter;
import com.example.cafeonline.adapter.ToppingAdapter;
import com.example.cafeonline.api.ApiService;
import com.example.cafeonline.api.DrinkApiService;
import com.example.cafeonline.api.UserApiService;
import com.example.cafeonline.model.response.AddressResponse;
import com.example.cafeonline.model.response.ApiResponse;
import com.example.cafeonline.model.response.DrinkResponse;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity {

    private ImageView imgBack;
    private Button btnAddAddress;
    private RecyclerView recyclerView;
    private AddressAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        imgBack = findViewById(R.id.img_toolbar_back);
        imgBack.setOnClickListener(v -> onBackPressed());
        btnAddAddress = findViewById(R.id.btn_add_address);
        btnAddAddress.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddAddressActivicty.class);
            startActivity(intent);
            finish();
        });
        recyclerView = findViewById(R.id.rcv_address);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadAddresses();

    }
    private void loadAddresses(){
        int userId = getUserIdFromPreferences();
        UserApiService service = ApiService.createService(UserApiService.class);
        Call<ApiResponse<List<AddressResponse>>> callApiDrink = service.getAllAddresses(userId);
        callApiDrink.enqueue(new Callback<ApiResponse<List<AddressResponse>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<AddressResponse>>> call, Response<ApiResponse<List<AddressResponse>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<AddressResponse>> apiResponse = response.body();
                    if ("200".equals(apiResponse.getValue().getStatus())) {
                        List<AddressResponse> addressResponseList = apiResponse.getValue().getData();

                        setupAdapter(addressResponseList);


                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(AddressActivity.this, "Error fetching addresses", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        if (response.errorBody() != null) {
                            Gson gson = new Gson();
                            ApiResponse<String> errorResponse = gson.fromJson(response.errorBody().string(), ApiResponse.class);
                            System.out.println(errorResponse.getValue().getMessage());
                            Toast.makeText(AddressActivity.this, "Error fetching addresses", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddressActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(AddressActivity.this, "Error parsing response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println(e.getMessage());
                    }
                }
            }



            @Override
            public void onFailure(Call<ApiResponse<List<AddressResponse>>> call, Throwable t) {
                Toast.makeText(AddressActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private int getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("KooheePrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("userId", 0); // Returns null if no userId is found
    }

    private void setupAdapter(List<AddressResponse> addressResponseList) {
        adapter = new AddressAdapter(addressResponseList, new AddressAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(AddressResponse address) {
                Toast.makeText(AddressActivity.this,"Selected Address: " + address.getName() + ", " + address.getPhone() + ", " + address.getAddress(),Toast.LENGTH_SHORT).show();
                saveAddressPreferences(address.getAddressId(), address.getName(), address.getPhone(), address.getAddress() );
            }

            @Override
            public void onDeleteClick(AddressResponse address) {
                deleteAddress(address);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void deleteAddress(AddressResponse address){
        int addressId = address.getAddressId();
        int userId = getUserIdFromPreferences();
        UserApiService service = ApiService.createService(UserApiService.class);
        Call<ApiResponse<String>> callApiDrink = service.deleteAddress(userId, addressId);
        callApiDrink.enqueue(new Callback<ApiResponse<String>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<String> apiResponse = response.body();
                    if ("200".equals(apiResponse.getValue().getStatus())) {
                        adapter.deleteAddress(address);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(AddressActivity.this, "Address deleted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddressActivity.this, "Error fetching addresses", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        if (response.errorBody() != null) {
                            Gson gson = new Gson();
                            ApiResponse<String> errorResponse = gson.fromJson(response.errorBody().string(), ApiResponse.class);
                            System.out.println(errorResponse.getValue().getMessage());
                            Toast.makeText(AddressActivity.this, "Error fetching addresses", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddressActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(AddressActivity.this, "Error parsing response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println(e.getMessage());
                    }
                }
            }



            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                Toast.makeText(AddressActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveAddressPreferences(int addressId, String fullName, String phone, String address) {
        SharedPreferences sharedPreferences = getSharedPreferences("KooheePrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("addressId", addressId);
        editor.putString("fullName", fullName);
        editor.putString("phone", phone);
        editor.putString("address", address);
        editor.apply();
    }


}

