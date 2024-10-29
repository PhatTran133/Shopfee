package com.example.cafeonline;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeonline.adapter.ChatBoxAdapter;
import com.example.cafeonline.model.ChatMessage;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChatBoxActivity extends AppCompatActivity {
    private RecyclerView recyclerViewMessages;
    private ChatBoxAdapter chatAdapter;
    private List<ChatMessage> messageList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_box);

        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
        messageList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        chatAdapter = new ChatBoxAdapter(messageList);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessages.setAdapter(chatAdapter);

        loadChatMessages();
    }

    private void loadChatMessages() {
        db.collection("message") // Tên collection trong Firestore
                .orderBy("createdDate") // Sắp xếp theo thời gian
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String message = document.getString("content");
                            Timestamp timestamp = document.getTimestamp("createdDate");
                            if (message != null && timestamp != null) {
                                messageList.add(new ChatMessage(message, timestamp));
                            }
                        }
                        chatAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView
                    } else {
                        Log.w("ChatActivity", "Error getting documents.", task.getException());
                    }
                });
    }
}