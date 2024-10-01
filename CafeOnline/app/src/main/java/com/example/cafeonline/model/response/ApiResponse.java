package com.example.cafeonline.model.response;

public class ApiResponse<T> {
    private Value<T> value;

    public Value<T> getValue() {
        return value;
    }

    public void setValue(Value<T> value) {
        this.value = value;
    }

    // Nested class for the Value
    public static class Value<T> {
        private String status;
        private String message;
        private T data;

        // Getters and Setters
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }
}

