package com.example.cafeonline.model.request;

import java.util.Date;

public class UserProfileRequest {
    public int Id;

    public UserProfileRequest(int id) {

        Id = id;
    }

    public UserProfileRequest() {
    }



    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
