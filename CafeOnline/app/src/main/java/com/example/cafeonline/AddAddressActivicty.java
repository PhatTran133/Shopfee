package com.example.cafeonline;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AddAddressActivicty extends AppCompatActivity {
    private ImageView imgBack;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        imgBack = findViewById(R.id.img_toolbar_back);
        imgBack.setOnClickListener(v -> onBackPressed());

        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}
