package com.example.cafeonline.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cafeonline.R;
import com.example.cafeonline.model.response.DrinkResponse;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {
    private List<DrinkResponse> drinkList;

    public DrinkAdapter(List<DrinkResponse> drinkList) {
        this.drinkList = drinkList;
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drink, parent, false);
        return new DrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {
        DrinkResponse drink = drinkList.get(position);
        holder.bind(drink);
    }

    @Override
    public int getItemCount() {
        return drinkList != null ? drinkList.size() : 0;
    }


    public class DrinkViewHolder extends RecyclerView.ViewHolder {
            private TextView tvName, tvPrice, tvCategoryName;
            private CircleImageView imageView;

            public DrinkViewHolder(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tv_name);
                tvPrice = itemView.findViewById(R.id.tv_price_sale);
                tvCategoryName=itemView.findViewById(R.id.tv_category_name);
                imageView = itemView.findViewById(R.id.img_drink);

            }

            // Hàm bind để hiển thị dữ liệu topping
            public void bind(DrinkResponse drink) {
                tvName.setText(drink.getName());
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                String formattedPrice = decimalFormat.format(drink.getPrice());
                tvPrice.setText(formattedPrice);
                Glide.with(itemView.getContext())
                        .load(drink.getImage())
                        .into(imageView);
            }
        }
    }


