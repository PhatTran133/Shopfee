package com.example.cafeonline.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;

import com.example.cafeonline.MainActivity;
import com.example.cafeonline.R;

public class NotificationService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null; // Không cần thiết vì Service này không sử dụng bound service
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Tạo notification channel khi Service khởi động
        createNotificationChannel();
        // Hiển thị notification khi Service khởi động
        showNotification();
        return START_NOT_STICKY;
    }

    // Tạo Notification Channel cho Android 8.0 trở lên
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "my_channel_id";
            CharSequence name = "My Channel";
            String description = "Channel for notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);

            // Đăng ký channel với hệ thống
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Hiển thị thông báo
    private void showNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "my_channel_id")
                .setSmallIcon(R.drawable.ic_logo)  // Icon nhỏ
                .setContentTitle("Tiêu đề thông báo")      // Tiêu đề
                .setContentText("Nội dung thông báo")      // Nội dung
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)  // Độ ưu tiên
                .setContentIntent(pendingIntent)           // Gán Intent
                .setAutoCancel(true);                      // Tự động hủy khi người dùng nhấn vào

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
