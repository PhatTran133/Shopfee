package com.example.cafeonline.model.request;

import java.util.Date;

public class UserProfileRequest {
    public int Id;
    public String Username;
    public String Email;
    public String Password;
    public String Phone;
    public String Address;
    public Date CreatedDate;
    public Date UpdatedDate;
    public UserProfileRequest(Date updatedDate, Date createdDate, String address, String phone, String password, String email, String username, int id) {
        UpdatedDate = updatedDate;
        CreatedDate = createdDate;
        Address = address;
        Phone = phone;
        Password = password;
        Email = email;
        Username = username;
        Id = id;
    }

    public UserProfileRequest() {
    }

    public Date getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        UpdatedDate = updatedDate;
    }

    public Date getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(Date createdDate) {
        CreatedDate = createdDate;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
