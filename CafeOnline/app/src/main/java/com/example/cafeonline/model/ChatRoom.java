package com.example.cafeonline.model;

public class ChatRoom {
    private int userId;

    public ChatRoom(){}

    public ChatRoom(int userId){
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
