package com.example.cafeonline.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cafeonline.R;
import com.example.cafeonline.model.response.OrderItemResponse;
import com.example.cafeonline.model.response.ToppingResponse;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderItemViewHolder>{
    private List<OrderItemResponse> orderItemResponseList;
//    private ToppingAdapter.OnToppingSelectedListener onToppingSelectedListener;



    public OrderAdapter(List<OrderItemResponse> orderItemResponseList) {
        this.orderItemResponseList = orderItemResponseList;
//        this.onToppingSelectedListener = listener;
    }

    @NonNull
    @Override
    public OrderAdapter.OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(parent.getContext(), R.layout.item_order, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);
        return new OrderAdapter.OrderItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderItemViewHolder holder, int position) {
        OrderItemResponse item = orderItemResponseList.get(position);
        holder.bind(item);

    }

    @Override
    public int getItemCount() {
        return orderItemResponseList != null ? orderItemResponseList.size() : 0;
    }

    public class OrderItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvPrice, tvQuantity, tvId;
        private CircleImageView imageView;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_drinks_name);
            tvPrice = itemView.findViewById(R.id.tv_total);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            tvId = itemView.findViewById(R.id.tv_order_id);
            imageView = itemView.findViewById(R.id.img_drink);

        }



        @SuppressLint("SetTextI18n")
        public void bind(OrderItemResponse item) {
            tvName.setText(item.getDrinkDTO().getName());
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String formattedPrice = decimalFormat.format(item.getTotalPrice());
            tvPrice.setText(formattedPrice + " VND");
            String formattedQuantity = decimalFormat.format((item.getQuantity()));
            tvQuantity.setText("x"+formattedQuantity);
            String formattedId = decimalFormat.format(item.getId());
            tvId.setText("ID: "+ formattedId);
            Glide.with(itemView.getContext())
                    .load(item.getDrinkDTO().getImage())
                    .into(imageView);
        }
    }
}
