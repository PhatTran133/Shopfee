package com.example.cafeonline;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class AddressActivity extends AppCompatActivity {
    private ImageView imgBack;
    private Button btnAddAddress;

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
        });
    }
}
