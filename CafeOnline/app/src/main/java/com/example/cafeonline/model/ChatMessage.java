package com.example.cafeonline.model;

import java.sql.Timestamp;

public class ChatMessage {
    private String content;
    private com.google.firebase.Timestamp time;
    private String roomId;
    private String userId;


    public ChatMessage() {
        // Cần một constructor mặc định cho Firebase
    }

    public ChatMessage(String content, com.google.firebase.Timestamp time, String roomId, String userId) {
        this.content = content;
        this.time = time;
        this.roomId = roomId;
        this.userId = userId;
    }

    public com.google.firebase.Timestamp getTime() {
        return time;
    }

    public void setTime(com.google.firebase.Timestamp time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String message) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
