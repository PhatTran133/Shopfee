package com.example.cafeonline;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordNotLoginActivity extends AppCompatActivity {
    private EditText newPassword, confirmPassword;
    private Button btnResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
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
        } else if (cPassword.equalsIgnoreCase(nPassword)) {
            Toast.makeText(this, "Confirm password is not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create login request object
//        LoginRequest loginRequest = new LoginRequest(email, password);
        ResetPasswordRequest request = new ResetPasswordRequest(nPassword,cPassword);
        // Create AuthService instance
        UserApiService authService = ApiService.createService(UserApiService.class);

        // Make API call
        Call<ApiResponse<UserResponse>> call = authService.resetPassword(request);
        call.enqueue(new Callback<ApiResponse<UserResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserResponse>> call, Response<ApiResponse<UserResponse>> response) {
                System.out.println("Response received");
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<UserResponse> apiResponse = response.body();

                    if ("200".equals(apiResponse.getValue().getStatus())) {
                        // Login successful
                        Toast.makeText(ChangePasswordNotLoginActivity.this, "Change password successfully", Toast.LENGTH_SHORT).show();
                        System.out.println("Change password successfully");
                    } else {
                        // Login failed with specific message
                        Toast.makeText(ChangePasswordNotLoginActivity.this, "Change password failed: " + apiResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                    }   System.out.println("Change password failed");
                } else {
                    // Handle error case
                    Toast.makeText(ChangePasswordNotLoginActivity.this, "Change password failed: Invalid response", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<UserResponse>> call, Throwable t) {
                System.out.println("Failure occurred: " + t.getMessage());
                Toast.makeText(ChangePasswordNotLoginActivity.this, "Change password failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
