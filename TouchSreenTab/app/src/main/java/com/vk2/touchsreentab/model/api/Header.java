package com.vk2.touchsreentab.model.api;

public class Header {
    private String Authorization;
    private String Accept;

    public Header() {
    }

    public String getAuthorization() {
        return Authorization;
    }

    public void setAuthorization(String authorization) {
        Authorization = authorization;
    }

    public String getAccept() {
        return Accept;
    }

    public void setAccept(String accept) {
        Accept = accept;
    }
}
