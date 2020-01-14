package com.vk2.touchsreentab.utils;

import android.databinding.ObservableBoolean;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

public class Utils {
    public static String intToTime(int milliseconds) {
        if (milliseconds == 0) {
            return "0:00";
        }
        long minutes = TimeUnit.MILLISECONDS.toMinutes((long) milliseconds);
        long seconds = TimeUnit.MILLISECONDS.toSeconds((long) milliseconds);
        long subSecond = seconds / 60;
        if ((seconds - subSecond * 60) < 9) {
            return minutes + ":0" + ((seconds - subSecond * 60) + 1);
        }
        return minutes + ":" + ((seconds - subSecond * 60) + 1);
    }

    public static String getDeviceId() {
        return "1006120003601000";
    }

    public static String O2J(Object obj) {
        return new Gson().toJson(obj);
    }

    public static ObservableBoolean isCloud(){
        ObservableBoolean b = new ObservableBoolean();
        b.set(true);
        return b;
    }
}
