package com.example.cafeonline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.example.cafeonline.model.ChatRoom;
import com.example.cafeonline.ui.HomeFragment;
import com.example.cafeonline.service.NotificationService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button btnSearch;
    private FirebaseFirestore db;
    private int userId;
    private String roomId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }
        int userId = getUserIdFromPreferences();

        db = FirebaseFirestore.getInstance();
//    ĐỪNG XÓA COMMENT NÀY
//        Intent intent = new Intent(MainActivity.this, DrinkDetailActivity.class);
//        startActivity(intent);

        //region Bottom Nav
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            } else if (item.getItemId() == R.id.nav_cart) {

                //Thêm if else để check đăng nhập chưa
                //Nếu chưa thì sang screen login
                if (userId == 0)
                {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return true;
                }
                //Rồi thì sang screen giỏ hàng
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
                return true;
            }else if (item.getItemId() == R.id.nav_history) {

                //Thêm if else để check đăng nhập chưa
                //Nếu chưa thì sang screen login
                if (userId == 0)
                {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return true;
                }
                //Rồi thì sang screen đơn hàng
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_account) {

                //Thêm if else để check đăng nhập chưa
                //Nếu chưa thì sang screen login
                if (userId == 0)
                {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return true;
                }
                //Nếu rồi thì sang screen account
                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
        //endregion

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        // Gọi Service để hiển thị notification
        Intent serviceIntent = new Intent(this, NotificationService.class);
        serviceIntent.putExtra("title", "KooHee");
        serviceIntent.putExtra("text", "Welcome " + userId + " to KooHee!" );
        startService(serviceIntent);
    }

    private int getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("KooheePrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("userId", 0);
    }

    private String getUserNameFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("KooheePrefs", MODE_PRIVATE);
        return sharedPreferences.getString("userName", "0");
    }

    public void onClickChat(View view) {
        userId = getUserIdFromPreferences();
        db.collection("room")
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                        // Đã có room với userId này, không thêm room mới
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            roomId = document.getString("roomId");
                            openChatBoxActivity(roomId);
                        }
                    } else {
                        // Không tìm thấy room với userId này, thêm room mới
                        db.collection("room")
                                .add(new ChatRoom(null, userId, getUserNameFromPreferences()))
                                .addOnSuccessListener(documentReference -> {
                                    roomId = documentReference.getId();
                                    // Cập nhật lại roomId trên Firestore
                                    db.collection("room")
                                            .whereEqualTo("userId", userId)
                                            .get()
                                            .addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task1.getResult()) {
                                                        // Kiểm tra xem roomId đã được thiết lập hay chưa
                                                        if (document.getString("roomId") == null) {
                                                            // Cập nhật roomId trên Firestore
                                                            document.getReference().update("roomId", roomId)
                                                                    .addOnSuccessListener(aVoid -> {
                                                                        Log.d("Firestore Update", "RoomId updated for document: " + roomId);
                                                                        openChatBoxActivity(roomId);
                                                                    })
                                                                    .addOnFailureListener(e -> {
                                                                        Log.w("Firestore Update Error", "Error updating RoomId", e);
                                                                    });
                                                        }
                                                    }
                                                } else {
                                                    Log.w("Firestore Error", "Error getting documents.", task.getException());
                                                }
                                            });
                                })
                                .addOnFailureListener(e -> {
                                    // Xử lý lỗi nếu không thêm được room
                                    Toast.makeText(this, "Error adding room: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    // Xử lý lỗi nếu không truy vấn được
                    Toast.makeText(this, "Error checking room: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
//        if(userId == -1){
//            Intent intent = new Intent(this, AdminChatBoxActivity.class);
//            startActivity(intent);
//        } else{
//            Intent intent = new Intent(this, ChatBoxActivity.class);
//            startActivity(intent);
//        }
    }
    private void openChatBoxActivity(String roomId) {
        Intent intent;
        if (userId == -1) {
            intent = new Intent(this, AdminChatBoxActivity.class);
            intent.putExtra("roomIdOfAdmin", roomId);
        } else {
            intent = new Intent(this, ChatBoxActivity.class);
            intent.putExtra("roomId", roomId);
        }
        startActivity(intent);
    }
}