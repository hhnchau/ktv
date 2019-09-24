package com.vk2.touchsreentab.api;

import java.util.List;

public interface ACallback<T> {
    void response(List<T> list);
}