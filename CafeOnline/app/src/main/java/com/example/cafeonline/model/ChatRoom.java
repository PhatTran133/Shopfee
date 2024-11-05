package com.example.cafeonline.model;

public class ChatRoom {
    private String roomId;
    private int userId;
    private String userName;

    public ChatRoom(){}

    public ChatRoom(String roomId, int userId, String userName){
        this.roomId = roomId;
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
