package com.example.cafeonline;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChatBoxActivity extends AppCompatActivity {
    private RecyclerView recyclerViewMessages;
    private ChatBoxAdapter chatAdapter;
    private List<ChatMessage> messageList;
    private FirebaseFirestore db;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_box);

        editText = findViewById(R.id.editTextMessage);
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
        messageList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        chatAdapter = new ChatBoxAdapter(messageList);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessages.setAdapter(chatAdapter);

        //loadChatMessages();
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
                                messageList.add(new ChatMessage(message, timestamp, "1" , "1"));
                            }
                        }
                        chatAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView
                    } else {
                        Log.w("ChatActivity", "Error getting documents.", task.getException());
                    }
                });
    }

    public void onSendClick(View view){
        String messageText = editText.getText().toString();

        if (!messageText.isEmpty()) {
            ChatMessage newMessage = new ChatMessage(messageText, Timestamp.now(), "1", "1"); // Tạo ChatMessage mới
            messageList.add(newMessage); // Thêm vào danh sách
            chatAdapter.notifyItemInserted(messageList.size() - 1); // Cập nhật RecyclerView

            // Lưu tin nhắn vào Firestore
            db.collection("message") // Thay đổi tên collection nếu cần
                    .add(newMessage)
                    .addOnSuccessListener(documentReference -> {
                        // Thêm thành công
                        editText.setText(""); // Xóa nội dung trong EditText sau khi gửi
                        recyclerViewMessages.scrollToPosition(messageList.size() - 1); // Cuộn tới item cuối
                    })
                    .addOnFailureListener(e -> {
                        // Xử lý lỗi nếu có
                        Toast.makeText(this, "Error sending message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}