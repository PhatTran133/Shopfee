package com.example.cafeonline.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeonline.R;
import com.example.cafeonline.model.PaymentMethod;

import java.util.List;
import android.widget.RadioButton;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {
    private List<PaymentMethod> paymentMethods;
    private String selectedName;
    private String selectedDescription;

    public PaymentAdapter(List<PaymentMethod> paymentMethods, String selectedName, String selectedDescription) {
        this.paymentMethods = paymentMethods;
        this.selectedName = selectedName; // Stored name
        this.selectedDescription = selectedDescription; // Stored description
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_method, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        PaymentMethod paymentMethod = paymentMethods.get(position);
        holder.tvName.setText(paymentMethod.getName());
        holder.tvDescription.setText(paymentMethod.getDescription());

        // Check if the current item matches the selected payment method
        holder.radioButton.setChecked(paymentMethod.getName().equals(selectedName) &&
                paymentMethod.getDescription().equals(selectedDescription));
        holder.radioButton.setOnClickListener(v -> {
            selectedName = paymentMethod.getName();
            selectedDescription = paymentMethod.getDescription();
            notifyDataSetChanged(); // Refresh the adapter to update checked states

            // Save to SharedPreferences
            SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("KooheePrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("selectedPaymentName", selectedName);
            editor.putString("selectedPaymentDescription", selectedDescription);
            editor.apply();
        });
        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            selectedName = paymentMethod.getName();
            selectedDescription = paymentMethod.getDescription();
            notifyDataSetChanged(); // Refresh the adapter to update checked states

            // Save to SharedPreferences
            SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("KooheePrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("selectedPaymentName", selectedName);
            editor.putString("selectedPaymentDescription", selectedDescription);
            editor.apply();
        });
    }

    @Override
    public int getItemCount() {
        return paymentMethods.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvDescription;
        private RadioButton radioButton;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDescription = itemView.findViewById(R.id.tv_description);
            radioButton = itemView.findViewById(R.id.radio_status);
        }
    }
}


