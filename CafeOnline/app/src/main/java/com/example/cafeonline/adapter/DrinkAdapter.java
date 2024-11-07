package com.example.cafeonline.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cafeonline.DrinkDetailActivity;
import com.example.cafeonline.R;
import com.example.cafeonline.model.response.DrinkResponse;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {
    private List<DrinkResponse> drinkList;
    private OnDrinkSelectedListener listener; // Thêm listener vào adapter

    // Constructor nhận listener
    public DrinkAdapter(List<DrinkResponse> drinkList, OnDrinkSelectedListener listener) {
        this.drinkList = drinkList;
        this.listener = listener;  // Lưu listener vào biến
    }

    public interface OnDrinkSelectedListener {
        void onDrinkSelected();
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
        private LinearLayout linearLayoutItemDrink;

        public DrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price_sale);
            tvCategoryName = itemView.findViewById(R.id.tv_category_name);
            imageView = itemView.findViewById(R.id.img_drink);
            linearLayoutItemDrink = itemView.findViewById(R.id.layout_item_drink);

        }

        @SuppressLint("SetTextI18n")
        public void bind(DrinkResponse drink) {
            tvName.setText(drink.getName());
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String formattedPrice = decimalFormat.format(drink.getPrice());
            tvPrice.setText(formattedPrice + " VND");
            Glide.with(itemView.getContext())
                    .load(drink.getImage())
                    .into(imageView);
            tvCategoryName.setText(drink.getCategoryName());
            linearLayoutItemDrink.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    DrinkResponse drink = drinkList.get(getAdapterPosition()); // Lấy vị trí item được click

                    Intent intent = new Intent(itemView.getContext(), DrinkDetailActivity.class);
                    intent.putExtra("drinkId", drink.getId());
                    itemView.getContext().startActivity(intent);


                }
            });
        }
    }
    public void updateDrinkList(List<DrinkResponse> newDrinkList) {
        this.drinkList.clear();
        this.drinkList.addAll(newDrinkList);
        notifyDataSetChanged(); // Thông báo cho adapter rằng dữ liệu đã thay đổi
    }

}



