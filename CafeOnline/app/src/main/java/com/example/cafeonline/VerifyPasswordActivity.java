package com.example.cafeonline;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cafeonline.api.ApiService;
import com.example.cafeonline.api.UserApiService;
import com.example.cafeonline.model.request.VerifyCodeRequest;
import com.example.cafeonline.model.response.ApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyPasswordActivity extends AppCompatActivity {
    private EditText emailEditText, OTPEditText;
    private Button btnResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText = findViewById(R.id.edt_email);
        OTPEditText = findViewById(R.id.textOtp);
        btnResetPassword=findViewById(R.id.btn_reset_password);
        btnResetPassword.setOnClickListener(v -> verifyCode());
    }


    private void verifyCode() {
        String email = emailEditText.getText().toString().trim();
        String OTP = OTPEditText.getText().toString().trim();

        // Check if fields are empty
        if (email.isEmpty() || OTP.isEmpty()) {
            Toast.makeText(this, "Please enter email and OTP", Toast.LENGTH_SHORT).show();
            return;
        }


//        ForgotPasswordRequest request = new ForgotPasswordRequest(email);
        VerifyCodeRequest request = new VerifyCodeRequest(email,OTP);

        UserApiService authService = ApiService.createService(UserApiService.class);

        // Make API call
        Call<ApiResponse<String>> call = authService.verifyCode(request);
        call.enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                System.out.println("Response received");
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<String> apiResponse = response.body();

                    if ("200".equals(apiResponse.getValue().getStatus())) {
                        // Login successful
                        Toast.makeText(VerifyPasswordActivity.this, "verified successfully", Toast.LENGTH_SHORT).show();
                        System.out.println("verified successfully");
                    } else {
                        // Login failed with specific message
                        Toast.makeText(VerifyPasswordActivity.this, "Invalid code: " + apiResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                    }   System.out.println("Invalid code");
                } else {
                    // Handle error case
                    Toast.makeText(VerifyPasswordActivity.this, "Invalid code: Invalid response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                System.out.println("Failure occurred: " + t.getMessage());
                Toast.makeText(VerifyPasswordActivity.this, "Invalid code: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
