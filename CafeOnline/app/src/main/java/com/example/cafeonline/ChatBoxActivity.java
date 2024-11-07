package com.example.cafeonline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeonline.adapter.ChatBoxAdapter;
import com.example.cafeonline.model.ChatMessage;
import com.example.cafeonline.model.ChatRoom;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ChatBoxActivity extends AppCompatActivity {
    private RecyclerView recyclerViewMessages;
    private ChatBoxAdapter chatAdapter;
    private List<ChatMessage> messageList;
    private FirebaseFirestore db;
    private EditText editText;
    private int userId;
    private String messageId;
    private String roomId;
    private String userName;

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

        loadChatMessages();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        roomId = intent.getStringExtra("roomId");
        Log.d("TESTTT01", roomId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onChangeListener();
    }

    private void loadChatMessages() {
        userId = getUserIdFromPreferences();
        db.collection("message") // Tên collection trong Firestore
                .orderBy("time", Query.Direction.ASCENDING) // Sắp xếp theo thời gian
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        messageList.clear(); // Xóa dữ liệu cũ nếu cần
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Lấy userId từ document dưới dạng Number
                            messageId = document.getId();
                            Long documentUserId = document.getLong("userId");
                            String content = document.getString("content");
                            Timestamp timestamp = document.getTimestamp("time");
                            String userName = document.getString("userName");
                            String roomId = document.getString("roomId");

                            // Kiểm tra userId
                            if (documentUserId != null && documentUserId.intValue() == userId || documentUserId.intValue() == -1) {
                                if (content != null && timestamp != null) {
                                    messageList.add(new ChatMessage(messageId, content, timestamp, roomId, userId, userName));
                                }
                            }
                        }
                        chatAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView
                    } else {
                        Log.w("ChatActivity", "Error getting documents.", task.getException());
                    }
                });
    }

    public void onSendClick(View view) {
        String messageText = editText.getText().toString();
        userId = getUserIdFromPreferences();
        userName = getUserNameFromPreferences();
        if (!messageText.isEmpty()) {
            // Tạo một ChatMessage mới với messageId và roomId ban đầu là null
            ChatMessage newMessage = new ChatMessage(null, messageText, Timestamp.now(), roomId, userId, userName); // Tạo ChatMessage mới
            // Lưu tin nhắn vào Firestore
            db.collection("message") // Thay đổi tên collection nếu cần
                    .add(newMessage)
                    .addOnSuccessListener(documentReference -> {
                        // Lấy documentId và cập nhật vào newMessage
                        messageId = documentReference.getId(); // Lấy documentId
                        newMessage.setMessageId(messageId); // Cập nhật vào đối tượng ChatMessage

                        // Cập nhật lại messageId trên Firestore
                        db.collection("message")
                                .whereEqualTo("userId", userId)
                                .get()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            // Kiểm tra xem messageId đã được thiết lập hay chưa
                                            if (document.getString("messageId") == null) {
                                                // Cập nhật messageId trên Firestore
                                                document.getReference().update("messageId", messageId)
                                                        .addOnSuccessListener(aVoid -> {
                                                            Log.d("Firestore Update", "MessageId updated for document: " + messageId);
                                                        })
                                                        .addOnFailureListener(e -> {
                                                            Log.w("Firestore Update Error", "Error updating messageId", e);
                                                        });
                                            }
                                        }
                                    } else {
                                        Log.w("Firestore Error", "Error getting documents.", task.getException());
                                    }
                                });
                        // Cập nhật messageList và RecyclerView
                        //messageList.add(newMessage); // Thêm vào danh sách
                        //chatAdapter.notifyItemInserted(messageList.size() - 1); // Cập nhật RecyclerView
                        editText.setText(""); // Xóa nội dung trong EditText sau khi gửi
                        recyclerViewMessages.scrollToPosition(messageList.size() - 1); // Cuộn tới item cuối
                    })
                    .addOnFailureListener(e -> {
                        // Xử lý lỗi nếu có
                        Toast.makeText(this, "Error sending message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private int getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("KooheePrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("userId", 0); // Returns null if no userId is found
    }

    private String getUserNameFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("KooheePrefs", MODE_PRIVATE);
        return sharedPreferences.getString("userName", "UserName"); // Returns null if no userId is found
    }

    private String getRoomIdAlreadyCreateFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("KooheePrefs", MODE_PRIVATE);
        return sharedPreferences.getString("roomIdAlreadyCreate", "0");
    }

    private String getRoomIdFirstCreateFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("KooheePrefs", MODE_PRIVATE);
        return sharedPreferences.getString("roomIdFirstCreate", "0");
    }

    private void onChangeListener(){
        db.collection("message")
                //.whereEqualTo("userId", userId)
                .whereIn("userId", Arrays.asList(-1, userId))
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("Message Change Error", "listen:error", e);
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            ChatMessage message = dc.getDocument().toObject(ChatMessage.class);
                            message.setMessageId(dc.getDocument().getId()); // Set messageId

                            switch (dc.getType()) {
                                case ADDED:
                                    if (!messageList.contains(message)) {
                                        messageList.add(message);
                                        chatAdapter.notifyItemInserted(messageList.size() - 1);
                                        chatAdapter.notifyDataSetChanged();
                                        Log.d("Message Change Added", "New message: " + dc.getDocument().getData());
                                    }
                                    Log.d("Message Change Added", "New message: " + dc.getDocument().getData());
                                    break;
                                case MODIFIED:
                                    for (int i = 0; i < messageList.size(); i++) {
                                        if (messageList.get(i).getMessageId().equals(message.getMessageId())) {
                                            messageList.set(i, message); // Cập nhật nội dung mới
                                            chatAdapter.notifyItemChanged(i); // Thông báo cập nhật cho adapter
                                            break;
                                        }
                                    }
                                    Log.d("Message Change Modified", "Modified message: " + dc.getDocument().getData());
                                    break;
                                case REMOVED:
                                    // Xử lý tin nhắn bị xóa
                                    for (int i = 0; i < messageList.size(); i++) {
                                        if (messageList.get(i).getMessageId().equals(message.getMessageId())) {
                                            messageList.remove(i); // Xóa tin nhắn
                                            chatAdapter.notifyItemRemoved(i); // Cập nhật RecyclerView
                                            break;
                                        }
                                    }
                                    Log.d("Message Change Removed", "Removed message: " + dc.getDocument().getData());
                                    break;
                            }
                        }
                        chatAdapter.notifyDataSetChanged(); // Cập nhật lại RecyclerView
                    }
                });

    }
}