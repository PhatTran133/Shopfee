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
import com.example.cafeonline.model.response.AddressResponse;
import com.example.cafeonline.model.response.DrinkResponse;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder>{
    private List<AddressResponse> addressList;
    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new AddressAdapter.AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
           AddressResponse response = addressList.get(position);
           holder.bind(response);
    }

    @Override
    public int getItemCount() {
        return addressList != null ? addressList.size() : 0;
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvPhone, tvAdress;



        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvAdress = itemView.findViewById(R.id.tv_address);


        }

        public void bind(AddressResponse address) {
            tvName.setText(address.getName());
            tvPhone.setText(address.getPhone());
            tvAdress.setText(address.getAddress());


        }
    }

}
