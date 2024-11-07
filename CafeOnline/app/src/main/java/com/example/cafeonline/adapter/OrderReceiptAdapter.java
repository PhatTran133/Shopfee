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
import com.example.cafeonline.model.response.CartItemToppingResponse;
import com.example.cafeonline.model.response.DrinkResponse;
import com.example.cafeonline.model.response.OrderItemResponse;
import com.example.cafeonline.model.response.OrderResponse;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderReceiptAdapter extends RecyclerView.Adapter<OrderReceiptAdapter.OrderReceiptViewHolder>{
    private List<OrderResponse> orderList;

    public OrderReceiptAdapter(List<OrderResponse> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderReceiptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(parent.getContext(), R.layout.item_order, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);
        return new OrderReceiptAdapter.OrderReceiptViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderReceiptAdapter.OrderReceiptViewHolder holder, int position) {
        OrderResponse item = orderList.get(position);
        holder.bind(item);

    }

    @Override
    public int getItemCount() {
        return orderList != null ? orderList.size() : 0;
    }

    public class OrderReceiptViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvPrice, tvQuantity, tvOption;
        private CircleImageView imageView;

        public OrderReceiptViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_order_id);
            tvPrice = itemView.findViewById(R.id.tv_total);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            tvOption = itemView.findViewById(R.id.tv_drinks_name);
            imageView = itemView.findViewById(R.id.img_drink);

        }



        @SuppressLint("SetTextI18n")
        public void bind(OrderResponse item) {
            List<OrderItemResponse> orderItemResponses = item.getOrderItemDTOs();
            for(OrderItemResponse response : orderItemResponses){
                tvName.setText(response.getDrinkDTO().getName());
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                String formattedQuantity = decimalFormat.format((response.getQuantity()));
                tvQuantity.setText("x"+formattedQuantity);
                Glide.with(itemView.getContext())
                        .load(response.getDrinkDTO().getImage())
                        .into(imageView);
                StringBuilder optionsBuilder = new StringBuilder();

                // Thêm số lượng
//            optionsBuilder.append("Số lượng: ").append(cart.getQuantity()).append(", ");

                // Thêm biến thể (nếu có)
                if (response.getVariant() != null && !response.getVariant().isEmpty()) {
                    optionsBuilder.append("Variant: ").append(response.getVariant()).append(", ");
                }

                // Thêm kích cỡ (nếu có)
                if (response.getSize() != null && !response.getSize().isEmpty()) {
                    optionsBuilder.append("Size: ").append(response.getSize()).append(", ");
                }

                // Thêm độ ngọt (nếu có)
                if (response.getSugar() != null && !response.getSugar().isEmpty()) {
                    optionsBuilder.append("Sugar: ").append(response.getSugar()).append(", ");
                }

                // Thêm có đá (nếu có)
                if (response.getIced() != null && !response.getIced().isEmpty()) {
                    optionsBuilder.append("Iced: ").append(response.getIced()).append(", ");
                }



                if (response.getOrderItemToppingDTOs() != null && !response.getOrderItemToppingDTOs().isEmpty()) {
                    StringBuilder toppingsBuilder = new StringBuilder();
                    toppingsBuilder.append("Toppings: ");

                    for (CartItemToppingResponse cartItemTopping : response.getOrderItemToppingDTOs()) {
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
            }
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String formattedPrice = decimalFormat.format(item.getTotal());
            tvPrice.setText(formattedPrice + " VND");



        }
    }
    }

