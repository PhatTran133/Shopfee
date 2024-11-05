package com.example.cafeonline.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeonline.ChatBoxActivity;
import com.example.cafeonline.R;
import com.example.cafeonline.model.ChatRoom;

import java.util.List;

public class AdminChatBoxAdapter extends RecyclerView.Adapter<AdminChatBoxAdapter.RoomViewHolder> {
    private List<ChatRoom> roomList;
    private OnChatBoxClickListener onChatBoxClickListener;

    public interface OnChatBoxClickListener {
        void onChatBoxClick(ChatRoom room);
    }

    public AdminChatBoxAdapter(List<ChatRoom> roomList, OnChatBoxClickListener onChatBoxClickListener) {
        this.roomList = roomList;
        this.onChatBoxClickListener = onChatBoxClickListener;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_admin_chat_box, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        ChatRoom room = roomList.get(position);
        holder.userName.setText(room.getUserName());
        holder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TEST", "Position is click");
                onChatBoxClickListener.onChatBoxClick(room);
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.textViewRoomName);
        }
    }
}
