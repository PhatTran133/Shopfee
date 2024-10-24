package com.example.cafeonline.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cafeonline.CartActivity;
import com.example.cafeonline.R;
import com.example.cafeonline.model.response.CartItemToppingResponse;
import com.example.cafeonline.model.response.CartResponse;
import com.example.cafeonline.model.response.CartItemResponse;
import com.example.cafeonline.model.response.ToppingResponse;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItemResponse> cartList;
    private DrinkAdapter.OnDrinkSelectedListener listener; //
    private CartActivity activity;
    public CartAdapter(List<CartItemResponse> cartList, DrinkAdapter.OnDrinkSelectedListener listener,CartActivity activity) {
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
              holder.bind(cart);
        holder.tvAdd.setOnClickListener(v -> {
            int newQuantity = cart.getQuantity() + 1;
            double newPrice = cart.getTotalPrice() * newQuantity;

            holder.tvQuantity.setText(String.valueOf(newQuantity));
            cart.setQuantity(newQuantity);
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String formattedPrice = decimalFormat.format(newPrice);
            holder.tvPrice.setText(String.valueOf(formattedPrice + "VND"));
            cart.setTotalPrice((int)newPrice);

            updateTotalPrice();  // Call method to update total price
        });

        holder.tvSub.setOnClickListener(v -> {
            int newQuantity = cart.getQuantity() - 1;
            if (newQuantity < 0) {
                newQuantity = 0; //
            }
            double newPrice = cart.getTotalPrice() / cart.getQuantity() * newQuantity;

            holder.tvQuantity.setText(String.valueOf(newQuantity));
            cart.setQuantity(newQuantity);
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String formattedPrice = decimalFormat.format(newPrice);
            holder.tvPrice.setText(String.valueOf(formattedPrice + "VND"));
            cart.setTotalPrice((int)newPrice);

            updateTotalPrice();
        });
    }

    @Override
    public int getItemCount() {
        return cartList != null ? cartList.size() : 0;
    }
    private void updateTotalPrice() {
        double overallTotalPrice = 0;
        double totalPrice = 0;
        for (CartItemResponse item : cartList) {
            overallTotalPrice += (item.getTotalPrice() * item.getQuantity()) + (totalPrice * item.getQuantity());
        }
        activity.updateTotalPrice(overallTotalPrice);
    }
    public class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvPrice, tvQuantity,tvSub, tvAdd, tvOption,tvTotalPrice;
        private CircleImageView imageView;
        private LinearLayout linearLayoutItemDrink;
        private int count = 1;
        private double price = 0;
        private double totalPrice = 0;
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
            linearLayoutItemDrink = itemView.findViewById(R.id.layout_item_drink);

        }

        public void bind(CartItemResponse cart) {
            // Xử lý phần hiển thị options
           tvName.setText(cart.getDrinkDTO().getName());
           DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String formattedPrice = decimalFormat.format(cart.getTotalPrice());
            tvPrice.setText(formattedPrice + "VND");
            String formattedQuantity = decimalFormat.format((cart.getQuantity()));
            tvQuantity.setText((formattedQuantity));
            Glide.with(itemView.getContext())
                    .load(cart.getDrinkDTO().getImage())
                    .into(imageView);

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



            if (cart.cartItemToppingDTOs != null && !cart.cartItemToppingDTOs.isEmpty()) {
                StringBuilder toppingsBuilder = new StringBuilder();
                toppingsBuilder.append("Toppings: ");

                for (CartItemToppingResponse cartItemTopping : cart.cartItemToppingDTOs) {
                    if (cartItemTopping != null && cartItemTopping.topping != null) {
                        toppingsBuilder.append(cartItemTopping.topping.name).append(", ");
                    }
                }

                // Xóa dấu phẩy thừa ở cuối
                if (toppingsBuilder.length() > 2) { // Đảm bảo ít nhất có 1 topping
                    toppingsBuilder.deleteCharAt(toppingsBuilder.length() - 2);
                }

                optionsBuilder.append(toppingsBuilder.toString());
            }
            // Xóa dấu phẩy thừa ở cuối
            optionsBuilder.reverse();
            int index = optionsBuilder.indexOf(",");
            if (index != -1) {
                optionsBuilder.deleteCharAt(index);
            }
            optionsBuilder.reverse();

            // Gán chuỗi tùy chọn đã xây dựng vào tvOption
            tvOption.setText(optionsBuilder.toString());

        }
    }
}


