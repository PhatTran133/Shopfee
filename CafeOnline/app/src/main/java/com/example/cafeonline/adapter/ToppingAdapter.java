package com.example.cafeonline.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeonline.R;
import com.example.cafeonline.model.response.ToppingResponse;

import java.text.DecimalFormat;
import java.util.List;

public class ToppingAdapter extends RecyclerView.Adapter<ToppingAdapter.ToppingViewHolder> {

    // Dữ liệu từ API là List<ToppingResponse>
    private List<ToppingResponse> toppingList;
    private OnToppingSelectedListener onToppingSelectedListener;

    public interface OnToppingSelectedListener {
        void onToppingSelected(int toppingId, double price, boolean isSelected);
    }

    // Sử dụng List<ToppingResponse>
    public ToppingAdapter(List<ToppingResponse> toppingList, OnToppingSelectedListener listener) {
        this.toppingList = toppingList;
        this.onToppingSelectedListener = listener;
    }

    @NonNull
    @Override
    public ToppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topping, parent, false);
        return new ToppingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToppingViewHolder holder, int position) {
        ToppingResponse topping = toppingList.get(position);
        holder.bind(topping);
    }

    @Override
    public int getItemCount() {
        return toppingList != null ? toppingList.size() : 0;
    }

    public class ToppingViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvPrice;
        private CheckBox checkBox;

        public ToppingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            checkBox = itemView.findViewById(R.id.cb_topping);

            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
               // check box được chọn hay không
                ToppingResponse topping = toppingList.get(getAdapterPosition());
                if (onToppingSelectedListener != null) {
                    onToppingSelectedListener.onToppingSelected(
                            topping.getId(),
                            topping.getPrice(),
                            isChecked
                    );
                }
            });
        }

        // Hàm bind để hiển thị dữ liệu topping
        public void bind(ToppingResponse topping) {
            tvName.setText(topping.getName());
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String formattedPrice = decimalFormat.format(topping.getPrice());
            tvPrice.setText(formattedPrice);
        }
    }
}
