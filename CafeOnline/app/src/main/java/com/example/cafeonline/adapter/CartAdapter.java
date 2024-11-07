package com.example.cafeonline.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cafeonline.CartActivity;
import com.example.cafeonline.DrinkDetailActivity;
import com.example.cafeonline.EditDrinkActivity;
import com.example.cafeonline.MainActivity;
import com.example.cafeonline.R;
import com.example.cafeonline.TrackingOrderActivity;
import com.example.cafeonline.model.request.CartItemRequestModel;
import com.example.cafeonline.model.response.AddressResponse;
import com.example.cafeonline.model.response.CartItemToppingResponse;
import com.example.cafeonline.model.response.CartResponse;
import com.example.cafeonline.model.response.CartItemResponse;
import com.example.cafeonline.model.response.ToppingResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItemResponse> cartList;
    private CartAdapter.OnItemClickListener listener; //
    private CartActivity activity;
    private int countItem;
    public interface OnItemClickListener {
        void onItemClick(CartItemResponse cartItemResponse);
        void onDeleteClick(CartItemResponse cartItemResponse);
    }
    public CartAdapter(List<CartItemResponse> cartList, CartAdapter.OnItemClickListener listener,CartActivity activity) {
        this.cartList = cartList;
        this.listener = listener;
        this.activity = activity;
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
              holder.bind(cart,position);
        holder.tvAdd.setOnClickListener(v -> {
            int newQuantity = cart.getQuantity() + 1;
            int newPrice = cart.getTotalPrice() * newQuantity;

            holder.tvQuantity.setText(String.valueOf(newQuantity));
            cart.setQuantity(newQuantity);
            cart.setUnitPrice(newPrice);
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String formattedPrice = decimalFormat.format(cart.getUnitPrice());
            holder.tvPrice.setText(String.valueOf(formattedPrice + " VND"));
            activity.updateCartItem(cart);
            updateTotalPrice();
        });

        holder.tvSub.setOnClickListener(v -> {
            int newQuantity = cart.getQuantity() - 1;
            if (newQuantity >= 1) {
                int newPrice = cart.getTotalPrice()  * newQuantity;
                holder.tvQuantity.setText(String.valueOf(newQuantity));
                cart.setQuantity(newQuantity);
                cart.setUnitPrice(newPrice);
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                String formattedPrice = decimalFormat.format(cart.getUnitPrice());
                holder.tvPrice.setText(String.valueOf(formattedPrice + " VND"));
                activity.updateCartItem(cart);
                updateTotalPrice();
            }

        });
    }

    @Override
    public int getItemCount() {
        return cartList != null ? cartList.size() : 0;
    }
    private void updateTotalPrice() {
        double totalPrice = 0;
        for (CartItemResponse item : cartList) {

            totalPrice += item.getUnitPrice();
        }
        activity.updateTotalPrice(totalPrice);
    }


    public void deleteCartItem(CartItemResponse cartItemResponse) {
        int position = cartList.indexOf(cartItemResponse);
        if (position != -1) {
            cartList.remove(position);
            notifyItemRemoved(position);
            updateTotalPrice();
        }
    }
    public class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvPrice, tvQuantity,tvSub, tvAdd, tvOption,tvTotalPrice;
        private CircleImageView imageView;
        private LinearLayout linearLayoutItemDrink;
        private int count = 1;
        private double price = 0;
        private double totalPrice = 0;
        private ImageView imgDelete, imgEdit;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_drink);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvQuantity = itemView.findViewById(R.id.tv_count);
            tvOption = itemView.findViewById(R.id.tv_option);
            imageView = itemView.findViewById(R.id.img_drink);
            tvAdd = itemView.findViewById(R.id.tv_add);
            tvSub = itemView.findViewById(R.id.tv_sub);
            tvTotalPrice= itemView.findViewById(R.id.tv_amount);
            imgDelete = itemView.findViewById(R.id.img_delete);
//            imgEdit = itemView.findViewById(R.id.img_edit);
            linearLayoutItemDrink = itemView.findViewById(R.id.layout_item_drink);
        }

        @SuppressLint("SetTextI18n")
        public void bind(CartItemResponse cart, int position) {
            // Xử lý phần hiển thị options
           tvName.setText(cart.getDrinkDTO().getName());
           DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String formattedPrice = decimalFormat.format(cart.getUnitPrice());
            tvPrice.setText(formattedPrice + " VND");
            String formattedQuantity = decimalFormat.format((cart.getQuantity()));
            tvQuantity.setText((formattedQuantity));
            Glide.with(itemView.getContext())
                    .load(cart.getDrinkDTO().getImage())
                    .into(imageView);
            countItem = getTotalQuantity();
            Log.d("TotalQuantity", "Tổng số lượng: " + countItem);
            StringBuilder optionsBuilder = new StringBuilder();

            // Thêm số lượng
//            optionsBuilder.append("Số lượng: ").append(cart.getQuantity()).append(", ");

            // Thêm biến thể (nếu có)
            if (cart.getVariant() != null && !cart.getVariant().isEmpty()) {
                optionsBuilder.append("Variant: ").append(cart.getVariant()).append(", ");
            }

            // Thêm kích cỡ (nếu có)
            if (cart.getSize() != null && !cart.getSize().isEmpty()) {
                optionsBuilder.append("Size: ").append(cart.getSize()).append(", ");
            }

            // Thêm độ ngọt (nếu có)
            if (cart.getSugar() != null && !cart.getSugar().isEmpty()) {
                optionsBuilder.append("Sugar: ").append(cart.getSugar()).append(", ");
            }

            // Thêm có đá (nếu có)
            if (cart.getIced() != null && !cart.getIced().isEmpty()) {
                optionsBuilder.append("Iced: ").append(cart.getIced()).append(", ");
            }



            if (cart.getCartItemToppingDTOs() != null && !cart.getCartItemToppingDTOs().isEmpty()) {
                StringBuilder toppingsBuilder = new StringBuilder();
                toppingsBuilder.append("Toppings: ");

                for (CartItemToppingResponse cartItemTopping : cart.getCartItemToppingDTOs()) {
                    if (cartItemTopping != null && cartItemTopping.getTopping() != null) {
                        toppingsBuilder.append(cartItemTopping.getTopping().getName()).append(", ");
//
                    }
                }
                toppingsBuilder.reverse();
                        int index = toppingsBuilder.indexOf(",");
                        if (index != -1) {
                            toppingsBuilder.deleteCharAt(index);
                        }
                        toppingsBuilder.reverse();
                optionsBuilder.append(toppingsBuilder.toString());
            }

            tvOption.setText(optionsBuilder.toString());
            imgDelete.setOnClickListener(v -> {
                if (position != RecyclerView.NO_POSITION) {

                    listener.onDeleteClick(cartList.get(position));
                }
            });
//            imgEdit.setOnClickListener(v -> {
//                Intent intent = new Intent(itemView.getContext(), EditDrinkActivity.class);
//                intent.putExtra("cartItemId", cart.getId());
//                intent.putExtra("drinkName", cart.getDrinkDTO().getName());
//                intent.putExtra("variant", cart.getVariant());
//                intent.putExtra("size", cart.getSize());
//                intent.putExtra("sugar", cart.getSugar());
//                intent.putExtra("iced", cart.getIced());
//                intent.putExtra("note", cart.getNote());
//                intent.putExtra("quantity", cart.getQuantity());
//                intent.putExtra("unitPrice", cart.getUnitPrice());
//
//                ArrayList<String> toppings = new ArrayList<>();
//                for (CartItemToppingResponse topping : cart.getCartItemToppingDTOs()) {
//                    toppings.add(topping.getTopping().getName());
//                }
//                intent.putStringArrayListExtra("toppings", toppings);
//
//                itemView.getContext().startActivity(intent);
//            });

        }
    }
    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (CartItemResponse item : cartList) {
            totalQuantity += item.getQuantity();
        }
        return totalQuantity;
    }
}

