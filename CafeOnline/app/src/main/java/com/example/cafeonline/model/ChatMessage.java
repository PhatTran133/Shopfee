package com.example.cafeonline.model;

import java.sql.Timestamp;

public class ChatMessage {
    private String content;
    private com.google.firebase.Timestamp time;
    private String roomId;
    private int userId;
    private String messageId;

    public ChatMessage() {
        // Cần một constructor mặc định cho Firebase
    }

    public ChatMessage(String messageId, String content, com.google.firebase.Timestamp time, String roomId, int userId) {
        this.content = content;
        this.time = time;
        this.roomId = roomId;
        this.userId = userId;
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
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

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
