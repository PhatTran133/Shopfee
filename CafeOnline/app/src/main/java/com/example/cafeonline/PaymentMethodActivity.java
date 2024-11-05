package com.example.cafeonline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeonline.adapter.PaymentAdapter;
import com.example.cafeonline.model.PaymentMethod;
import com.example.cafeonline.R;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethodActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PaymentAdapter adapter;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        imgBack = findViewById(R.id.img_toolbar_back);
        imgBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
            finish();
        });

        recyclerView = findViewById(R.id.rcv_payment_method);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create the list of payment methods
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        paymentMethods.add(new PaymentMethod("Cash", "VND"));
        paymentMethods.add(new PaymentMethod("Banking", "VNPay"));

        // Get selected payment method name and description from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("KooheePrefs", MODE_PRIVATE);
        String selectedName = sharedPreferences.getString("selectedPaymentName", "");
        String selectedDescription = sharedPreferences.getString("selectedPaymentDescription", "");

        // Initialize the adapter with the payment methods and selected values
        adapter = new PaymentAdapter(paymentMethods, selectedName, selectedDescription);
        recyclerView.setAdapter(adapter);
    }

}
