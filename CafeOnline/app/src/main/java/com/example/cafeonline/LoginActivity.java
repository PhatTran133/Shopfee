package com.example.cafeonline;

import android.content.Intent;
import android.os.Bundle;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private TextView btnForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnForgotPassword = findViewById(R.id.tv_forgot_password);
        // Set up click listener for login button
        btnLogin.setOnClickListener(v -> performLogin());
        btnForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent); // Chuyển sang ForgotPasswordActivity
        });
    }



    private void performLogin() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        // Check if fields are empty
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create login request object
        LoginRequest loginRequest = new LoginRequest(email, password);

        // Create AuthService instance
        UserApiService authService = ApiService.createService(UserApiService.class);

        // Make API call
        Call<ApiResponse<UserResponse>> call = authService.loginUser(loginRequest);
        call.enqueue(new Callback<ApiResponse<UserResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserResponse>> call, Response<ApiResponse<UserResponse>> response) {
                System.out.println("Response received");
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<UserResponse> apiResponse = response.body();

                    if ("200".equals(apiResponse.getValue().getStatus())) {
                        // Login successful
                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        System.out.println("Login Successfully");
                    } else {
                        // Login failed with specific message
                        Toast.makeText(LoginActivity.this, "Login Failed: " + apiResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                    }   System.out.println("Login Failed");
                } else {
                    // Handle error case
                    Toast.makeText(LoginActivity.this, "Login Failed: Invalid response", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<UserResponse>> call, Throwable t) {
                System.out.println("Failure occurred: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Login Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
