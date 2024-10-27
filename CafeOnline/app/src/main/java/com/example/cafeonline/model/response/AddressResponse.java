package com.example.cafeonline.model.response;

public class AddressResponse {
    private int addressId;
    private int userId;
    private String name;
    private String phone;
    private String address;

    public AddressResponse(int addressId, int userId, String name, String phone, String address) {
        this.addressId = addressId;
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
