package com.example.cafeonline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cafeonline.api.ApiService;
import com.example.cafeonline.api.UserApiService;
import com.example.cafeonline.model.request.ChangePasswordRequestModel;
import com.example.cafeonline.model.response.ApiResponse;
import com.example.cafeonline.model.response.UserResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText oldPassword, newPassword, confirmPassword;
    private Button btnChangePassword;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_login);
        imgBack = findViewById(R.id.img_toolbar_back);
        imgBack.setOnClickListener(v -> onBackPressed());

        newPassword = findViewById(R.id.edt_new_password);
        confirmPassword = findViewById(R.id.edt_confirm_password);
        btnChangePassword = findViewById(R.id.btn_change_password);
        oldPassword = findViewById(R.id.edt_old_password);
        // Set up click listener for login button
        btnChangePassword.setOnClickListener(v -> changePassword());

    }
    public void changePassword(){
        int userId = getUserIdFromPreferences();
        String oPassword = oldPassword.getText().toString().trim();
        String nPassword = newPassword.getText().toString().trim();
        String cPassword = confirmPassword.getText().toString().trim();

     if (nPassword.isEmpty() || cPassword.isEmpty() || oPassword.isEmpty()) {
            Toast.makeText(this, "Please enter all field required", Toast.LENGTH_SHORT).show();
            return;
        } else if (!cPassword.equalsIgnoreCase(nPassword)) {
            Toast.makeText(this, "Confirm password is not match", Toast.LENGTH_SHORT).show();
            return;
        }
        ChangePasswordRequestModel requestModel = new ChangePasswordRequestModel(userId, oPassword, nPassword, cPassword);
        UserApiService authService = ApiService.createService(UserApiService.class);

        // Make API call
        Call<ApiResponse<String>> call = authService.changePassword(requestModel);
        call.enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<String> apiResponse = response.body();
                    if ("200".equals(apiResponse.getValue().getStatus())) {
                        Toast.makeText(ChangePasswordActivity.this, "Change password successfully", Toast.LENGTH_SHORT).show();
                        System.out.println("Change password successfully");
                        Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, apiResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        // Check if error body exists
                        if (response.errorBody() != null) {
                            // Deserialize error body to ApiResponse<String>
                            Gson gson = new Gson();
                            ApiResponse<String> errorResponse = gson.fromJson(response.errorBody().string(), ApiResponse.class);
                            System.out.println(errorResponse.getValue().getMessage());
                            Toast.makeText(ChangePasswordActivity.this, "Fetch Failed: " + errorResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ChangePasswordActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(ChangePasswordActivity.this, "Error parsing response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("KooheePrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("userId", 0); // Returns null if no userId is found
    }
}
