package com.example.cafeonline.model.response;

public class AddressResponse {
    private int Id;
    private String name;
    private String phone;
    private String address;

    public AddressResponse(int id, String name, String phone, String address) {
        Id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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
