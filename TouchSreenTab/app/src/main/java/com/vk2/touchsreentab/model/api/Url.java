package com.vk2.touchsreentab.model.api;

public class Url {
    private String url;
    private Header header;

    public Url() {
    }

    public Url(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }
}
