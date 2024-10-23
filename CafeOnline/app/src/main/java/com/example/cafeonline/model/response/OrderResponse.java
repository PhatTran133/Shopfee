package com.example.cafeonline.model.response;

import java.util.Date;

public class OrderResponse {
    private int id;
    private int userId;
    private double total;
    private boolean statusOfOrder;
    private Date createdDate;
    private Date updateDate;
    private Date deletedDate;
    private UserResponse user;

    public OrderResponse(int id, int userId, double total, boolean statusOfOrder, Date createdDate, Date updateDate, Date deletedDate, UserResponse user) {
        this.id = id;
        this.userId = userId;
        this.total = total;
        this.statusOfOrder = statusOfOrder;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
        this.deletedDate = deletedDate;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isStatusOfOrder() {
        return statusOfOrder;
    }

    public void setStatusOfOrder(boolean statusOfOrder) {
        this.statusOfOrder = statusOfOrder;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
}
