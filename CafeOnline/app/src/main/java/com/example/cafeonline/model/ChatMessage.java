package com.example.cafeonline.model;

import java.sql.Timestamp;

public class ChatMessage {
    private String message;
    private com.google.firebase.Timestamp time;

    public ChatMessage() {
        // Cần một constructor mặc định cho Firebase
    }

    public ChatMessage(String message, com.google.firebase.Timestamp time) {
        this.message = message;
        this.time = time;
    }

    public com.google.firebase.Timestamp getTime() {
        return time;
    }

    public void setTime(com.google.firebase.Timestamp time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
