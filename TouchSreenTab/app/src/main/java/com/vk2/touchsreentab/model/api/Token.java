package com.vk2.touchsreentab.model.api;

public class Token{
    private String token;
    private double timeout;
    private String dApi;

    public Token() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public double getTimeout() {
        return timeout;
    }

    public void setTimeout(double timeout) {
        this.timeout = timeout;
    }

    public String getDApi() {
        return dApi;
    }

    public void setDApi(String dApi) {
        this.dApi = dApi;
    }
}
