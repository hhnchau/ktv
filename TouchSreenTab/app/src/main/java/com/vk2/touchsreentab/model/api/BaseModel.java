package com.vk2.touchsreentab.model.api;

public class BaseModel<T> {
    private int err;
    private String msg;
    private int total;
    private T data;

    BaseModel() {
    }

    public BaseModel(int err, String msg, T data) {
        this.err = err;
        this.msg = msg;
        this.data = data;
    }

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setData(T data) {
        this.data = data;
    }
}
