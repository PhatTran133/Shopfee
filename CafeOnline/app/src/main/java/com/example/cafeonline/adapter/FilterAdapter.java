package com.example.cafeonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeonline.R;
import com.example.cafeonline.model.response.DrinkResponse;

import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder> {
    private Context context;
    private List<String> filterItems;
    private LinearLayout selectedFilterLayout = null; // Store the currently selected layout

    public FilterAdapter(Context context, List<String> filterItems) {
        this.context = context;
        this.filterItems = filterItems;
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_filter, parent, false);
        return new FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder holder, int position) {
        String filterItem = filterItems.get(position);
        holder.tvTitle.setText(filterItem);

        // Reset background color of the layout
        if (selectedFilterLayout != null && selectedFilterLayout == holder.layoutItem) {
            holder.layoutItem.setBackgroundResource(R.drawable.bg_button_enable_corner_16); // Selected background
            holder.tvTitle.setTextColor(context.getResources().getColor(R.color.bgMainColor)); // Selected text color
        } else {
            holder.layoutItem.setBackgroundResource(R.drawable.bg_white_corner_16_border_gray); // Default background
            holder.tvTitle.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark)); // Default text color
        }

        // Set click listener on the entire layout
        holder.layoutItem.setOnClickListener(v -> {
            selectOption(holder.layoutItem);
        });
    }

    @Override
    public int getItemCount() {
        return filterItems.size();
    }

    static class FilterViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutItem; // Reference to the layout
        ImageView imgFilter;
        TextView tvTitle;

        public FilterViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layout_item); // Initialize the layout item
            imgFilter = itemView.findViewById(R.id.img_filter);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }

    private void selectOption(LinearLayout selectedLayout) {
        // Deselect previously selected layout
        if (selectedFilterLayout != null) {
            selectedFilterLayout.setBackgroundResource(R.drawable.bg_white_corner_16_border_gray);
            TextView previousTitle = selectedFilterLayout.findViewById(R.id.tv_title);
            previousTitle.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }

        // Select the new layout
        selectedFilterLayout = selectedLayout;
        selectedLayout.setBackgroundResource(R.drawable.bg_button_enable_corner_16);
        TextView selectedTitle = selectedLayout.findViewById(R.id.tv_title);
        selectedTitle.setTextColor(context.getResources().getColor(R.color.bgMainColor));
    }
}

