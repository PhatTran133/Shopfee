package com.example.cafeonline.adapter;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.cafeonline.R;
import com.example.cafeonline.model.response.AddressResponse;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    private List<AddressResponse> addressList;
    private OnItemClickListener listener;
    private int chosenAddressId;
    private int selectedPosition = -1;

    public interface OnItemClickListener {
        void onItemClick(AddressResponse address);
        void onDeleteClick(AddressResponse address);
    }

    public AddressAdapter(int chosenAddressId, List<AddressResponse> addressList, OnItemClickListener listener) {
        this.chosenAddressId = chosenAddressId;
        this.addressList = addressList;
        this.listener = listener;

        // Set initial selected position based on chosenAddressId
        for (int i = 0; i < addressList.size(); i++) {
            if (addressList.get(i).getAddressId() == chosenAddressId) {
                selectedPosition = i;
                break;
            }
        }
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        AddressResponse response = addressList.get(position);
        holder.bind(response, position);
    }

    @Override
    public int getItemCount() {
        return addressList != null ? addressList.size() : 0;
    }

    public void deleteAddress(AddressResponse address) {
        int position = addressList.indexOf(address);
        if (position != -1) {
            addressList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvPhone, tvAdress;
        private RadioButton radioButton;
        private ImageView imgDelete;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvAdress = itemView.findViewById(R.id.tv_address);
            radioButton = itemView.findViewById(R.id.radio_status);
            imgDelete = itemView.findViewById(R.id.img_delete);
        }

        public void bind(AddressResponse address, int position) {
            tvName.setText(address.getName());
            tvPhone.setText(address.getPhone());
            tvAdress.setText(address.getAddress());

            // Set the radio button checked state based on selectedPosition
            radioButton.setChecked(position == selectedPosition);

            radioButton.setOnClickListener(v -> {
                selectedPosition = position; // Update selected position on click
                notifyItemRangeChanged(0, addressList.size()); // Notify all items for refresh
                if (listener != null) {
                    listener.onItemClick(addressList.get(position));
                }
            });

            imgDelete.setOnClickListener(v -> {
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(addressList.get(position));
                }
            });
        }
    }
}


