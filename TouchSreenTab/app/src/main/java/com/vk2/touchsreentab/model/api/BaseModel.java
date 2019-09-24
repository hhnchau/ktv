package com.vk2.touchsreentab.model.api;

import android.os.Parcelable;

public class BaseModel<T extends Parcelable> {
    private String msg;
    private int err;
    private T data;

    public BaseModel(String msg, int err, T data) {
        this.msg = msg;
        this.err = err;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
