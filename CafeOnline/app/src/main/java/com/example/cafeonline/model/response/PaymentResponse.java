package com.example.cafeonline.model.response;

import java.util.Date;

public class PaymentResponse {
         private int id;
         private String type;
         private String detail;
    private Date createdDate;
    private Date updateDate;

    public PaymentResponse(int id, String type, String detail, Date createdDate, Date updateDate) {
        this.id = id;
        this.type = type;
        this.detail = detail;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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
}
