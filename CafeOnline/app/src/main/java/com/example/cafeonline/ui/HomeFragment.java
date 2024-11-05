package com.example.cafeonline.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.cafeonline.R;
import com.example.cafeonline.adapter.DrinkAdapter;
import com.example.cafeonline.adapter.FilterAdapter;
import com.example.cafeonline.api.ApiService;
import com.example.cafeonline.api.DrinkApiService;
import com.example.cafeonline.model.response.ApiResponse;
import com.example.cafeonline.model.response.DrinkResponse;
import com.example.cafeonline.widget.CustomTabLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private ImageSlider imageSlider;
    private Button btnSearch;
    private EditText search;
    private CustomTabLayout tabCategory, tabFilter;
    private RecyclerView rvFilterItems;
    private String selectedCategory = "Coffee";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout của Fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        search = rootView.findViewById(R.id.edt_search_name);
        btnSearch = rootView.findViewById(R.id.btn_search);

        btnSearch.setOnClickListener(v -> getDrinkData(selectedCategory));

        tabCategory = rootView.findViewById(R.id.tab_category);
        addTabs();

        rvFilterItems = rootView.findViewById(R.id.rv_filter_items);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvFilterItems.setLayoutManager(layoutManager);

        // Example filter items
        List<String> filterItems = Arrays.asList("All", "Price", "Name");
        // Tạo FilterAdapter với callback
        FilterAdapter filterAdapter = new FilterAdapter(getContext(), filterItems, filter -> {
            boolean descPrice = false;
            boolean ascName = false;

            // Cập nhật trạng thái boolean dựa trên lựa chọn
            switch (filter) {
                case "All":
                    // Cả descPrice và ascName đều false
                    descPrice = false;
                    ascName = false;
                    break;
                case "Price":
                    // Chỉ bật descPrice
                    descPrice = true;
                    ascName = false;
                    break;
                case "Name":
                    // Chỉ bật ascName
                    descPrice = false;
                    ascName = true;
                    break;
            }
            Log.d("FilterSelection", "Filter: " + filter + ", descPrice: " + descPrice + ", ascName: " + ascName);

            // Gọi API với các tham số cập nhật
            getDrinkForFilter(descPrice, ascName);
        });        rvFilterItems.setAdapter(filterAdapter);


        recyclerView = rootView.findViewById(R.id.rcv_drink_home);

        imageSlider = rootView.findViewById(R.id.imageSlider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.drink_example, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.ic_logo, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.ic_splash, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.drink_example, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getDrinkData(selectedCategory);

        return rootView;
    }

    private void getDrinkData(String category) {
        String name = search.getText().toString().trim();
        String size = search.getText().toString().trim();
        DrinkApiService drinkService = ApiService.createService(DrinkApiService.class);
        retrofit2.Call<ApiResponse<List<DrinkResponse>>> callApiDrink = drinkService.getDrinkFilter(name, category, size);
        callApiDrink.enqueue(new Callback<ApiResponse<List<DrinkResponse>>>() {
            @Override
            public void onResponse(retrofit2.Call<ApiResponse<List<DrinkResponse>>> callApiDrink, Response<ApiResponse<List<DrinkResponse>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<DrinkResponse>> apiResponse = response.body();
                    if ("200".equals(apiResponse.getValue().getStatus())) {
                        List<DrinkResponse> drinkList = apiResponse.getValue().getData();

                        if (recyclerView.getAdapter() instanceof DrinkAdapter) {
                            DrinkAdapter adapter = (DrinkAdapter) recyclerView.getAdapter();
                            adapter.updateDrinkList(drinkList); // Gọi phương thức updateDrinkList
                        } else {
                            // Nếu adapter chưa được khởi tạo, tạo mới
                            DrinkAdapter adapter = new DrinkAdapter(drinkList, null);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                } else {
                    try {
                        if (response.errorBody() != null) {
                            Gson gson = new Gson();
                            ApiResponse<String> errorResponse = gson.fromJson(response.errorBody().string(), ApiResponse.class);
                            System.out.println(errorResponse.getValue().getMessage());
                            Toast.makeText(getContext(), "Fetch Failed: " + errorResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Error parsing response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<DrinkResponse>>> call, Throwable t) {
                Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addTabs() {
        tabCategory.addTab(tabCategory.newTab().setText("Coffee"));
        tabCategory.addTab(tabCategory.newTab().setText("MilkTea"));
        tabCategory.addTab(tabCategory.newTab().setText("Juice"));

        // Set a listener to handle tab selection
        tabCategory.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String selectedTab = tab.getText().toString();
                selectedCategory = selectedTab;
                getDrinkData(selectedCategory);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                String selectedTab = tab.getText().toString();
//                selectedCategory = selectedTab;
//                getDrinkData(selectedCategory);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                String selectedTab = tab.getText().toString();
//                selectedCategory = selectedTab;
//                getDrinkData(selectedCategory);
            }
        });
    }

    private void getDrinkForFilter(boolean descPrice, boolean ascName) {
        // Tạo một instance của API service và gọi API
        DrinkApiService drinkApi = ApiService.createService(DrinkApiService.class);
        Call<ApiResponse<List<DrinkResponse>>> call = drinkApi.getDrinkForFilter(descPrice, ascName);

        call.enqueue(new Callback<ApiResponse<List<DrinkResponse>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<DrinkResponse>>> call, Response<ApiResponse<List<DrinkResponse>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<DrinkResponse>> apiResponse = response.body();
                    if ("200".equals(apiResponse.getValue().getStatus())) {
                        List<DrinkResponse> drinkList = apiResponse.getValue().getData();

                        if (recyclerView.getAdapter() instanceof DrinkAdapter) {
                            DrinkAdapter adapter = (DrinkAdapter) recyclerView.getAdapter();
                            adapter.updateDrinkList(drinkList); // Gọi phương thức updateDrinkList
                        } else {
                            // Nếu adapter chưa được khởi tạo, tạo mới
                            DrinkAdapter adapter = new DrinkAdapter(drinkList, null);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<DrinkResponse>>> call, Throwable t) {
                Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();            }
        });
    }
}


