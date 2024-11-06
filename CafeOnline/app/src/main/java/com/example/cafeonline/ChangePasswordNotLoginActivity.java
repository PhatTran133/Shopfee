package com.example.cafeonline;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cafeonline.api.ApiService;
import com.example.cafeonline.api.UserApiService;
import com.example.cafeonline.model.request.ResetPasswordRequest;
import com.example.cafeonline.model.response.ApiResponse;
import com.example.cafeonline.model.response.UserResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordNotLoginActivity extends AppCompatActivity {
    private EditText newPassword, confirmPassword;
    private Button btnResetPassword;
    private String email = null;
    private String otp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_not_login);

        // Initialize views
        newPassword = findViewById(R.id.edt_new_password);
        confirmPassword = findViewById(R.id.edt_confirm_password);
        btnResetPassword = findViewById(R.id.btn_reset_password);

        // Set up click listener for login button
        btnResetPassword.setOnClickListener(v -> resetPassword());


    }

    private void resetPassword() {

        String nPassword = newPassword.getText().toString().trim();
        String cPassword = confirmPassword.getText().toString().trim();

        // Check if fields are empty
        if (nPassword.isEmpty() || cPassword.isEmpty()) {
            Toast.makeText(this, "Please enter both new password and confirm password", Toast.LENGTH_SHORT).show();
            return;
        } else if (!cPassword.equalsIgnoreCase(nPassword)) {
            Toast.makeText(this, "Confirm password is not match", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = getIntent();
        if (intent != null) {
             email = intent.getStringExtra("email");
             otp = intent.getStringExtra("otp");
            // Sử dụng email và OTP truyề từ trang ForgotPasswordActivity
        }
        // Create login request object
//        LoginRequest loginRequest = new LoginRequest(email, password);
        ResetPasswordRequest request = new ResetPasswordRequest(email.toString().trim(), otp.toString().trim(), nPassword);
        // Create AuthService instance
        UserApiService authService = ApiService.createService(UserApiService.class);

        // Make API call
        Call<ApiResponse<ResetPasswordRequest>> call = authService.resetPassword(request);
        call.enqueue(new Callback<ApiResponse<ResetPasswordRequest>>() {
            @Override
            public void onResponse(Call<ApiResponse<ResetPasswordRequest>> call, Response<ApiResponse<ResetPasswordRequest>> response) {
                System.out.println("Response received");
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<ResetPasswordRequest> apiResponse = response.body();

                    if ("200".equals(apiResponse.getValue().getStatus())) {
                        // Login successful
                        Toast.makeText(ChangePasswordNotLoginActivity.this, "Change password successfully", Toast.LENGTH_SHORT).show();
                        System.out.println("Change password successfully");
                        Intent intent = new Intent(ChangePasswordNotLoginActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Login failed with specific message
                        Toast.makeText(ChangePasswordNotLoginActivity.this, "Change password failed: " + apiResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                    }   System.out.println("Change password failed");
                } else {
                    // Handle error case
                    try {
                        // Check if error body exists
                        if (response.errorBody() != null) {
                            // Deserialize error body to ApiResponse<String>
                            Gson gson = new Gson();
                            ApiResponse<String> errorResponse = gson.fromJson(response.errorBody().string(), ApiResponse.class);
                            Toast.makeText(ChangePasswordNotLoginActivity.this, "Reset Password Failed: " + errorResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ChangePasswordNotLoginActivity.this, "Error: Invalid response", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(ChangePasswordNotLoginActivity.this, "Error parsing response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }                }

            }
            @Override
            public void onFailure(Call<ApiResponse<ResetPasswordRequest>> call, Throwable t) {
                System.out.println("Failure occurred: " + t.getMessage());
                Toast.makeText(ChangePasswordNotLoginActivity.this, "Change password failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
