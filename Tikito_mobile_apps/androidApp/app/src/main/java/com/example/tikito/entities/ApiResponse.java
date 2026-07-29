package com.example.tikito.entities;

public class ApiResponse<T> {

    private String status;
    private T data;

    public ApiResponse() {
    }

    public String getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(T data) {
        this.data = data;
    }

}
