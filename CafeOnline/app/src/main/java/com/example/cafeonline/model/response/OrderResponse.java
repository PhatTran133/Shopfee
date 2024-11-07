package com.example.cafeonline.model.response;

import java.util.Date;
import java.util.List;

public class OrderResponse {
    private int id;
    private int userId;
    private double total;
    private boolean statusOfOrder;
    private Date createdDate;
    private Date updateDate;
    private Date deletedDate;
    private List<OrderItemResponse> orderItemDTOs;
    private List<PaymentResponse> paymentDTOs;

    public OrderResponse(List<PaymentResponse> paymentDTOs, List<OrderItemResponse> orderItemDTOs, Date deletedDate, Date updateDate, Date createdDate, boolean statusOfOrder, double total, int userId, int id) {
        this.paymentDTOs = paymentDTOs;
        this.orderItemDTOs = orderItemDTOs;
        this.deletedDate = deletedDate;
        this.updateDate = updateDate;
        this.createdDate = createdDate;
        this.statusOfOrder = statusOfOrder;
        this.total = total;
        this.userId = userId;
        this.id = id;
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

    public List<OrderItemResponse> getOrderItemDTOs() {
        return orderItemDTOs;
    }

    public void setOrderItemDTOs(List<OrderItemResponse> orderItemDTOs) {
        this.orderItemDTOs = orderItemDTOs;
    }

    public List<PaymentResponse> getPaymentDTOs() {
        return paymentDTOs;
    }

    public void setPaymentDTOs(List<PaymentResponse> paymentDTOs) {
        this.paymentDTOs = paymentDTOs;
    }
}
