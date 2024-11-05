package com.example.cafeonline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeonline.adapter.AdminChatBoxAdapter;
import com.example.cafeonline.model.ChatMessage;
import com.example.cafeonline.model.ChatRoom;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminChatBoxActivity extends AppCompatActivity {
    private RecyclerView recyclerViewRooms;
    private RecyclerView recyclerUserViewMessages;
    private AdminChatBoxAdapter chatAdapter;
    private List<ChatRoom> roomList;
    private List<ChatMessage> userChatList;
    private List<ChatMessage> messageList;
    private FirebaseFirestore db;
    private int userId;
    private String roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_chat_box);

        recyclerViewRooms = findViewById(R.id.recyclerViewRooms);
        roomList = new ArrayList<>();
        userChatList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        chatAdapter = new AdminChatBoxAdapter(roomList, this::onChatBoxClick);
        recyclerViewRooms.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRooms.setAdapter(chatAdapter);

        loadRooms();
    }

    private void loadRooms() {
        db.collection("room") // Tên collection trong Firestore
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        roomList.clear(); // Xóa dữ liệu cũ nếu cần
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            roomId = document.getId();
                            Integer userIdNow = document.getLong("userId").intValue();
                            userId = userIdNow;
                            String userName = document.getString("userName");
                            roomList.add(new ChatRoom(roomId, userIdNow, userName));
                        }
                        chatAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView
                    } else {
                        Log.w("AdminChatActivity", "Error getting documents.", task.getException());
                    }
                });
    }

    private void onChatBoxClick(ChatRoom room) {
        Intent intent = new Intent(this, AdminChatBoxUserActivity.class);
        intent.putExtra("roomId", room.getRoomId()); // Truyền roomId vào Intent
        intent.putExtra("userId", room.getUserId());
        startActivity(intent);
    }


    private void onChangeListener(){
    }
}