package com.example.cafeonline;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cafeonline.api.ApiService;
import com.example.cafeonline.api.UserApiService;
import com.example.cafeonline.model.request.RegisterRequest;
import com.example.cafeonline.model.request.VerifyCodeRequest;
import com.example.cafeonline.model.response.ApiResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword, edtUsername, edtOTP, edtPhone, edtAddress;
    private Button btnSendOTP, btnRegister;
    private TextView txtLogin;
    private ImageView imgBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        edtUsername = findViewById(R.id.edt_username);
        edtOTP = findViewById(R.id.edt_Otp);
        edtPhone = findViewById((R.id.edt_phone));
        edtAddress = findViewById((R.id.edt_address));
        btnSendOTP = findViewById(R.id.btn_sendOTP);
        btnRegister = findViewById(R.id.btn_register);
        txtLogin = findViewById(R.id.tv_login);
        imgBack = findViewById(R.id.img_toolbar_back);

        btnRegister.setOnClickListener(v -> verifiedOTP());
        btnSendOTP.setOnClickListener(v -> getOTP() );
        txtLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            finish();
            startActivity(intent);
        });
        imgBack.setOnClickListener(v -> onBackPressed());
    }

    private void getOTP(){
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String userName = edtUsername.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (password.isEmpty()) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (userName.isEmpty()) {
            Toast.makeText(this, "Please enter user name", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (phone.isEmpty()) {
            Toast.makeText(this, "Please enter phone number", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (address.isEmpty()) {
            Toast.makeText(this, "Please enter address", Toast.LENGTH_SHORT).show();
            return;
        }


        RegisterRequest registerRequest = new RegisterRequest(userName,email,password,phone,address);
        UserApiService authService = ApiService.createService(UserApiService.class);
        Call<ApiResponse<String>> call = authService.registerUser(registerRequest);
        call.enqueue(new Callback<ApiResponse<String>>(){
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response){
                if(response.isSuccessful() && response.body() != null){
                    ApiResponse<String> apiResponse = response.body();

                    if("200".equals(apiResponse.getValue().getStatus())){
                        Toast.makeText(RegisterActivity.this,"Register Successfully" +  apiResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Register Failed" + apiResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    try {
                        // Check if error body exists
                        if (response.errorBody() != null) {
                            // Deserialize error body to ApiResponse<String>
                            Gson gson = new Gson();
                            ApiResponse<String> errorResponse = gson.fromJson(response.errorBody().string(), ApiResponse.class);
                            Toast.makeText(RegisterActivity.this, "Register Failed: " + errorResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error: Invalid response", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(RegisterActivity.this, "Error parsing response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t){
                Toast.makeText(RegisterActivity.this, "Register Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void verifiedOTP(){
        String email = edtEmail.getText().toString().trim();
        String otp = edtOTP.getText().toString().trim();

        if (email.isEmpty() || otp.isEmpty()) {
            Toast.makeText(this, "Please enter email and otp to verify", Toast.LENGTH_SHORT).show();
            return;
        }
        VerifyCodeRequest verifyCodeRequest = new VerifyCodeRequest(email,otp);
        UserApiService authService = ApiService.createService(UserApiService.class);
        Call<ApiResponse<Integer>> call = authService.verifyCodeForRegister(verifyCodeRequest);
        call.enqueue(new Callback<ApiResponse<Integer>>() {
            @Override
            public void onResponse(Call<ApiResponse<Integer>> call, Response<ApiResponse<Integer>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<Integer> apiResponse = response.body();

                    if ("200".equals(apiResponse.getValue().getStatus())) {
                        Toast.makeText(RegisterActivity.this, "Register Successfully: UserId: " + apiResponse.getValue().getData(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Register Failed: " + apiResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } try {
                    // Check if error body exists
                    if (response.errorBody() != null) {
                        // Deserialize error body to ApiResponse<String>
                        Gson gson = new Gson();
                        ApiResponse<String> errorResponse = gson.fromJson(response.errorBody().string(), ApiResponse.class);
                        Toast.makeText(RegisterActivity.this, "Register Failed: " + errorResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Error: Invalid response", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(RegisterActivity.this, "Error parsing response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Integer>> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Register Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
