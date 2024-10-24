package com.example.cafeonline.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cafeonline.R;
import com.example.cafeonline.model.response.CartResponse;
import com.example.cafeonline.model.response.CartItemResponse;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItemResponse> cartList;
    private DrinkAdapter.OnDrinkSelectedListener listener; //

    public CartAdapter(List<CartItemResponse> cartList, DrinkAdapter.OnDrinkSelectedListener listener) {
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
              CartItemResponse cart = cartList.get(position);
              holder.bind(cart);
    }

    @Override
    public int getItemCount() {
        return cartList != null ? cartList.size() : 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvPrice, tvQuantity, tvOption;
        private CircleImageView imageView;
        private LinearLayout linearLayoutItemDrink;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_drink);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvQuantity = itemView.findViewById(R.id.tv_count);
            tvOption = itemView.findViewById(R.id.tv_option);
            imageView = itemView.findViewById(R.id.img_drink);
            linearLayoutItemDrink = itemView.findViewById(R.id.layout_item_drink);

        }

        public void bind(CartItemResponse cart) {
            // Xử lý phần hiển thị options
           tvName.setText(cart.getDrinkDTO().getName());
           DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String formattedPrice = decimalFormat.format(cart.getPrice());
            tvPrice.setText(formattedPrice);
            String formattedQuantity = decimalFormat.format((cart.getQuantity()));
            tvQuantity.setText((formattedQuantity));
            Glide.with(itemView.getContext())
                    .load(cart.getDrinkDTO().getImage())
                    .into(imageView);
//            tvQuantity.setText(cart.getQuantity();

        }
    }
}


