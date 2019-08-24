package com.jxmfkj.www.myapplication.api;

public class WrapperRspEntity<T> {
    private int code;
    private T data;
    private String msg;

    public WrapperRspEntity() {
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
