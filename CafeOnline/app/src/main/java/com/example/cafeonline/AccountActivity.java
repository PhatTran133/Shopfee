package com.example.cafeonline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class AccountActivity  extends AppCompatActivity {
    private ImageView imgBack;
    private LinearLayout update_profile, log_out, change_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_account);

        update_profile =  findViewById(R.id.update_profile);
        update_profile.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, UserProfileActivity.class);
            startActivity(intent);
        });
        change_password = findViewById(R.id.layout_change_password);
        change_password.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        });
        log_out =  findViewById(R.id.logout);
        log_out.setOnClickListener(v -> {
            // Xóa userID
            clearUserIdFromPreferences();
            Intent intent = new Intent(AccountActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        imgBack = findViewById(R.id.img_toolbar_back);
        imgBack.setOnClickListener(v -> onBackPressed());
    }
    private void clearUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("KooheePrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("userId");
        editor.apply();
    }

}
