package com.example.cafeonline;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cafeonline.model.response.AddressResponse;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PaymentCallbackActivity extends AppCompatActivity {
    private TextView tvTransactionCode, tvDateTime, tvTotal, tvPaymentMethod, tvFullname, tvPhone, tvAddress;
    private Button btTrackingOrder;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_order);

        tvTransactionCode = findViewById(R.id.tv_id_transaction);
        tvDateTime = findViewById(R.id.tv_date_time);
        tvTotal = findViewById(R.id.tv_total);
        tvPaymentMethod = findViewById(R.id.tv_payment_method);
        tvFullname = findViewById(R.id.tv_name);
        tvPhone = findViewById(R.id.tv_phone);
        tvAddress = findViewById(R.id.tv_address);
        btTrackingOrder = findViewById(R.id.tv_tracking_order);
        imgBack = findViewById(R.id.img_toolbar_back);
        imgBack.setOnClickListener(v -> onBackPressed());

        Intent intent = getIntent();
        Uri uri = intent.getData();

        if (uri != null) {
            String vnp_ResponseCode = uri.getQueryParameter("vnp_ResponseCode");
            String vnp_Amount = uri.getQueryParameter("vnp_Amount");
            String vnp_TransactionNo = uri.getQueryParameter("vnp_TransactionNo");

            if ("00".equals(vnp_ResponseCode)) {
                tvTransactionCode.setText(vnp_TransactionNo);
                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                String formattedDate = dateFormat.format(currentDate);
                tvDateTime.setText(formattedDate);
                tvPaymentMethod.setText("VNPay");
                long amountInCents = Long.parseLong(vnp_Amount);
                double amountInVND = amountInCents / 100.0;
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                String formattedAmount = decimalFormat.format(amountInVND);
                tvTotal.setText(formattedAmount + " VND");                AddressResponse addressResponse = getAddressFromPreferences();
                tvFullname.setText(addressResponse.getName());
                tvPhone.setText(addressResponse.getPhone());
                tvAddress.setText(addressResponse.getAddress());
                btTrackingOrder.setOnClickListener(v -> {
                    Intent orderIntent = new Intent(this, OrderActivity.class);
                    startActivity(orderIntent);
                    finish();
                });
            } else {
                Toast.makeText(this, "Failed to call back", Toast.LENGTH_SHORT).show();

                Intent errorIntent = new Intent(this, MainActivity.class);
                startActivity(errorIntent);
                finish();
            }
        }
    }

    private AddressResponse getAddressFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("KooheePrefs", MODE_PRIVATE);
        int addressId = sharedPreferences.getInt("addressId", -1);
        String fullname = sharedPreferences.getString("fullName", null);
        String phone = sharedPreferences.getString("phone", null);
        String address = sharedPreferences.getString("address", null);
        AddressResponse addressResponse = new AddressResponse(addressId, 0, fullname, phone, address);
        return addressResponse;
    }
}
