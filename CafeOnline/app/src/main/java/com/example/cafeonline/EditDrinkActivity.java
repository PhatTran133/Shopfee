package com.example.cafeonline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cafeonline.adapter.ToppingAdapter;
import com.example.cafeonline.api.ApiService;
import com.example.cafeonline.api.ToppingApiService;
import com.example.cafeonline.model.request.AddToCartRequest;
import com.example.cafeonline.model.response.ApiResponse;
import com.example.cafeonline.model.response.CartItemToppingResponse;
import com.example.cafeonline.model.response.ToppingResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDrinkActivity extends AppCompatActivity {

    private ImageView imgBack, imageDrink;
    private TextView tvName, tvDescription, tvPrice, tvSub, tvAdd, tvCount, tvTotal, tvAddToCart;
    private EditText edtNote;
    private TextView selectedVariant = null;
    private TextView selectedSize = null;
    private TextView selectedSugar = null;
    private TextView selectedIce = null;
    private RecyclerView recyclerView;
    private int count = 1;
    private double price = 0;
    List<AddToCartRequest.Topping> toppingIdList = new ArrayList<>();
    private double totalPrice = 0;
    private int drinkId = 1;
    private String note = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_detail);

        // Lấy các tham số từ Intent
        int cartItemId = getIntent().getIntExtra("cartItemId", -1);
        String drinkName = getIntent().getStringExtra("drinkName");
        String variant = getIntent().getStringExtra("variant");
        String size = getIntent().getStringExtra("size");
        String sugar = getIntent().getStringExtra("sugar");
        String iced = getIntent().getStringExtra("iced");
        String note = getIntent().getStringExtra("note");
        int quantity = getIntent().getIntExtra("quantity", 1);
        int unitPrice = getIntent().getIntExtra("unitPrice", 0);

        // Khởi tạo các View
        imgBack = findViewById(R.id.img_toolbar_back);
        imgBack.setOnClickListener(v -> onBackPressed());

        imageDrink = findViewById(R.id.img_drink);

        tvName = findViewById(R.id.tv_name);
        tvCount = findViewById(R.id.tv_count);
        tvAdd = findViewById(R.id.tv_add);
        tvSub = findViewById(R.id.tv_sub);
        tvSub.setOnClickListener(v -> {
            if (count > 1) {
                count--;
                tvCount.setText(String.valueOf(count));
                updateTotalPrice();
            }
        });

        tvAdd.setOnClickListener(v -> {
            count++;
            tvCount.setText(String.valueOf(count));
            updateTotalPrice();
        });

        recyclerView = findViewById(R.id.rcv_topping);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        edtNote = findViewById((R.id.edt_notes));
        edtNote.setText(note);
        tvTotal = findViewById(R.id.tv_total);

        //region SELECT OPTION
        TextView tvVariantIce = findViewById(R.id.tv_variant_ice);
        TextView tvVariantHot = findViewById(R.id.tv_variant_hot);

        tvVariantIce.setOnClickListener(v -> selectOption(tvVariantIce, "variant"));
        tvVariantHot.setOnClickListener(v -> selectOption(tvVariantHot, "variant"));

        TextView tvSizeSmall = findViewById(R.id.tv_size_small);
        TextView tvSizeMedium = findViewById(R.id.tv_size_medium);
        TextView tvSizeLarge = findViewById(R.id.tv_size_large);

        tvSizeSmall.setOnClickListener(v -> selectOption(tvSizeSmall, "size"));
        tvSizeMedium.setOnClickListener(v -> selectOption(tvSizeMedium, "size"));
        tvSizeLarge.setOnClickListener(v -> selectOption(tvSizeLarge, "size"));

        TextView tvSugarNormal = findViewById(R.id.tv_sugar_normal);
        TextView tvSugarLess = findViewById(R.id.tv_sugar_less);

        tvSugarNormal.setOnClickListener(v -> selectOption(tvSugarNormal, "sugar"));
        tvSugarLess.setOnClickListener(v -> selectOption(tvSugarLess, "sugar"));

        TextView tvIceNormal = findViewById(R.id.tv_ice_normal);
        TextView tvIceLess = findViewById(R.id.tv_ice_less);

        tvIceNormal.setOnClickListener(v -> selectOption(tvIceNormal, "ice"));
        tvIceLess.setOnClickListener(v -> selectOption(tvIceLess, "ice"));

        // Set các giá trị đã chọn
        tvName.setText(drinkName);
        setSelectedOption(variant, tvVariantIce, tvVariantHot);
        setSelectedOption(size, tvSizeSmall, tvSizeMedium, tvSizeLarge);
        setSelectedOption(sugar, tvSugarNormal, tvSugarLess);
        setSelectedOption(iced, tvIceNormal, tvIceLess);

        tvCount.setText(String.valueOf(quantity));
        price = unitPrice;
        //endregion

        // HIỂN THỊ TOPPING LIST
        ToppingApiService toppingService = ApiService.createService(ToppingApiService.class);
        Call<ApiResponse<List<ToppingResponse>>> callApiTopping = toppingService.getTopping();
        callApiTopping.enqueue(new Callback<ApiResponse<List<ToppingResponse>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ToppingResponse>>> callApiTopping, Response<ApiResponse<List<ToppingResponse>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<ToppingResponse>> apiResponse = response.body();
                    if ("200".equals(apiResponse.getValue().getStatus())) {
                        List<ToppingResponse> toppingResponse = apiResponse.getValue().getData();

                        // Hiển thị các topping được chọn
                        List<CartItemToppingResponse> selectedToppings = (ArrayList<CartItemToppingResponse>) getIntent().getSerializableExtra("toppings");
                        if (selectedToppings != null) {
                            for (CartItemToppingResponse selectedTopping : selectedToppings) {
                                for (ToppingResponse topping : toppingResponse) {
                                    if (selectedTopping.getTopping().getId() == topping.getId()) {

                                        topping.setSelected(true);
                                        toppingIdList.add(new AddToCartRequest.Topping(topping.getId()));
                                        totalPrice += topping.getPrice();
                                    }
                                }
                            }
                        }

                        // Thiết lập adapter với dữ liệu topping
                        ToppingAdapter adapter = new ToppingAdapter(toppingResponse, new ToppingAdapter.OnToppingSelectedListener() {
                            @Override
                            public void onToppingSelected(int toppingId, double price, boolean isSelected) {
                                if (isSelected) {
                                    AddToCartRequest.Topping topping = new AddToCartRequest.Topping(toppingId);
                                    toppingIdList.add(topping);
                                    totalPrice += price;
                                } else {
                                    toppingIdList.remove(Integer.valueOf(toppingId));
                                    totalPrice -= price;
                                }
                                updateTotalPrice();
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(EditDrinkActivity.this, "Error fetching toppings", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<ToppingResponse>>> call, Throwable t) {
                Toast.makeText(EditDrinkActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSelectedOption(String option, TextView... textViews) {
        for (TextView textView : textViews) {
            if (textView.getText().toString().equals(option)) {
                selectOption(textView, option);
                break;
            }
        }
    }

    private void selectOption(TextView selectedTextView, String type) {
        TextView previousSelection = null;

        switch (type) {
            case "variant":
                previousSelection = selectedVariant;
                selectedVariant = selectedTextView;
                break;
            case "size":
                previousSelection = selectedSize;
                selectedSize = selectedTextView;
                break;
            case "sugar":
                previousSelection = selectedSugar;
                selectedSugar = selectedTextView;
                break;
            case "ice":
                previousSelection = selectedIce;
                selectedIce = selectedTextView;
                break;
        }

        if (previousSelection != null) {
            previousSelection.setBackgroundResource(R.drawable.bg_white_corner_6_border_main);
            previousSelection.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        selectedTextView.setBackgroundResource(R.drawable.bg_main_corner_6);
        selectedTextView.setTextColor(getResources().getColor(R.color.bgMainColor));
    }

    private void updateTotalPrice() {
        double drinkPrice = price;
        double finalPrice = (drinkPrice * count) + (totalPrice * count);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        tvTotal.setText(decimalFormat.format(finalPrice) + " VND");
    }
}
