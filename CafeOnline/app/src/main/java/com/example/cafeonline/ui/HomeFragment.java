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
import com.example.cafeonline.MainActivity;
import com.example.cafeonline.R;
import com.example.cafeonline.adapter.DrinkAdapter;
import com.example.cafeonline.api.ApiService;
import com.example.cafeonline.api.DrinkApiService;
import com.example.cafeonline.model.response.ApiResponse;
import com.example.cafeonline.model.response.DrinkResponse;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private ImageSlider imageSlider;
    private Button btnSearch;
    private EditText search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout của Fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        search = rootView.findViewById(R.id.edt_search_name);
        btnSearch = rootView.findViewById(R.id.btn_search);

        btnSearch.setOnClickListener(v -> getDrinkData());

        // Lấy RecyclerView và ImageSlider từ layout của Fragment
        recyclerView = rootView.findViewById(R.id.rcv_drink_home);
        imageSlider = rootView.findViewById(R.id.imageSlider);
        //tạo array list cho ảnh trong slider
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        //Add ảnh vào slider
        slideModels.add(new SlideModel(R.drawable.drink_example, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.ic_logo, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.ic_splash, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.drink_example, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getDrinkData();

        return rootView;
    }

    private void getDrinkData() {
        String name = search.getText().toString().trim();
        String category = search.getText().toString().trim();
        String size = search.getText().toString().trim();

//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        try {
//            Date startDate = sdf.parse(search.getText().toString().trim());
//            Date endDate = sdf.parse(search.getText().toString().trim());
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        double maxPrice = double.(search.getText().toString().trim());
//        double minPrice = Double.parseDouble(search.getText().toString().trim());
        DrinkApiService drinkService = ApiService.createService(DrinkApiService.class);
        retrofit2.Call<ApiResponse<List<DrinkResponse>>> callApiDrink = drinkService.getDrinkFilter(name, category, size);
        callApiDrink.enqueue(new Callback<ApiResponse<List<DrinkResponse>>>() {
            @Override
            public void onResponse(retrofit2.Call<ApiResponse<List<DrinkResponse>>> callApiDrink, Response<ApiResponse<List<DrinkResponse>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<DrinkResponse>> apiResponse = response.body();
                    if ("200".equals(apiResponse.getValue().getStatus())) {
                        List<DrinkResponse> drinkList = apiResponse.getValue().getData();

                        DrinkAdapter adapter = new DrinkAdapter(drinkList, null);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(getContext(), apiResponse.getValue().getMessage(), Toast.LENGTH_SHORT).show();
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
}


