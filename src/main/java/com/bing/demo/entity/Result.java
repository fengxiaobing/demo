package com.bing.demo.entity;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private int result = 0;
    private  T data;
    private String message ="请求成功";

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
