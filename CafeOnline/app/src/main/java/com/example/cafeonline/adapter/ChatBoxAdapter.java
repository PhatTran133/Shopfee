package com.example.cafeonline.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeonline.R;
import com.example.cafeonline.model.ChatMessage;

import org.w3c.dom.Text;

import java.util.List;

public class ChatBoxAdapter extends RecyclerView.Adapter<ChatBoxAdapter.ChatViewHolder> {
    private List<ChatMessage> messageList;

    public ChatBoxAdapter(List<ChatMessage> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_box, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatMessage message = messageList.get(position);
        holder.textViewMessage.setText(message.getContent());
        holder.textViewUserName.setText(message.getUserName());
        if(message.getUserName().isEmpty()){
            holder.textViewUserName.setText("Admin");
        }
        // Chuyển đổi Timestamp thành String để hiển thị
//        if (message.getTime() != null) {
//            holder.textViewTime.setText(message.getTime().toString()); // Chỉnh sửa định dạng theo nhu cầu
//        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewMessage;
        public TextView textViewTime;
        public TextView textViewUserName;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.textViewMessage);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
        }
    }
}

