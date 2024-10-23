package com.example.cafeonline.adapter;

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
import com.example.cafeonline.model.response.CartResponse;
import com.example.cafeonline.model.response.DrinkResponse;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartResponse> cartList;
    private DrinkAdapter.OnDrinkSelectedListener listener; //

    public CartAdapter(List<CartResponse> cartList, DrinkAdapter.OnDrinkSelectedListener listener) {
        this.cartList = cartList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartAdapter.CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
//              CartResponse cart = cartList.get(position);
//              holder.bind(cart);
    }

    @Override
    public int getItemCount() {
        return cartList != null ? cartList.size() : 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvPrice, tvCategoryName;
        private CircleImageView imageView;
        private LinearLayout linearLayoutItemDrink;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price_sale);
            tvCategoryName = itemView.findViewById(R.id.tv_category_name);
            imageView = itemView.findViewById(R.id.img_drink);
            linearLayoutItemDrink = itemView.findViewById(R.id.layout_item_drink);

        }

//        public void bind(CartResponse cart) {
//            tvName.setText(drink.getName());
//            DecimalFormat decimalFormat = new DecimalFormat("#,###");
//            String formattedPrice = decimalFormat.format(drink.getPrice());
//            tvPrice.setText(formattedPrice);
//            Glide.with(itemView.getContext())
//                    .load(drink.getImage())
//                    .into(imageView);
//
//            linearLayoutItemDrink.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    CartResponse cart = cartList.get(getAdapterPosition()); // Lấy vị trí item được click
//
//                    Intent intent = new Intent(itemView.getContext(), DrinkDetailActivity.class);
//                    intent.putExtra("drinkId", cart.getId());
//                    itemView.getContext().startActivity(intent);
//
//                }
//            });
//        }
    }
}


