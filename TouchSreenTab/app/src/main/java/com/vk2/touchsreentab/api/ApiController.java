package com.vk2.touchsreentab.api;

public class ApiController {
    private static ApiController instance = null;

    public static ApiController getInstance() {
        if (instance == null) {
            instance = new ApiController();
        }
        return instance;
    }

}