package com.example.cafeonline.model.request;

public class OrderRequestModel {
    private int userId;
    private int cartId;
    private PaymentRequestDTO paymentRequest;

    public OrderRequestModel(int userId, int cartId, PaymentRequestDTO paymentRequest) {
        this.userId = userId;
        this.cartId = cartId;
        this.paymentRequest = paymentRequest;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public PaymentRequestDTO getPaymentRequest() {
        return paymentRequest;
    }

    public void setPaymentRequest(PaymentRequestDTO paymentRequest) {
        this.paymentRequest = paymentRequest;
    }

    public static class PaymentRequestDTO{
        private String type;
        private String detail;

        public PaymentRequestDTO(String type, String detail) {
            this.type = type;
            this.detail = detail;
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
    }
}


