package com.example.cafeonline.model.response;

public class PaymentUrlResponse {
    private String paymentUrl;

    public PaymentUrlResponse(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }
}
