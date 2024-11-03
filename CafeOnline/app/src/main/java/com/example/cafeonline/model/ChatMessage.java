package com.example.cafeonline.model;

import java.sql.Timestamp;

public class ChatMessage {
    private String messageId; // ID của tin nhắn
    private String content;
    private com.google.firebase.Timestamp time;
    private int roomId;
    private int userId;


    public ChatMessage() {
        // Cần một constructor mặc định cho Firebase
    }

    public ChatMessage(String messageId, String content, com.google.firebase.Timestamp time, int roomId, int userId) {
        this.messageId = messageId;
        this.content = content;
        this.time = time;
        this.roomId = roomId;
        this.userId = userId;
    }

    // Getter và Setter cho messageId
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

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
