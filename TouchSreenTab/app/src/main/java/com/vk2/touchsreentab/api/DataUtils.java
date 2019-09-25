package com.vk2.touchsreentab.api;

public class DataUtils {

    public static ApiService getDataOnline() {
        return Api.getInstance().create(Host.API_SOUNDCLOUD);
    }
}
