package com.vk2.touchsreentab.model.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.HashMap;
import java.util.Map;


public class PlayerViewModel extends ViewModel {
    private Map<String, Object> map = new HashMap<>();
    public static final int ACTION_PROGRESS = 1;
    public static final int ACTION_BROADCAST = 2;
    public static final int ACTION_SEEK = 3;
    public static final int ACTION_VOCAL = 4;
    public static final int ACTION_FINISHED = 5;

    private MutableLiveData<Map<String, Object>> mPlayer = new MutableLiveData<>();

    public MutableLiveData<Map<String, Object>> getPlayer() {
        return mPlayer;
    }

    public void setValue(int action, long duration, long progress) {
        map.put("action", action);
        map.put("duration", duration);
        map.put("progress", progress);
        this.mPlayer.setValue(map);
    }

    public void setFinish(int action){
        map.put("action", action);
        this.mPlayer.setValue(map);
    }

}
