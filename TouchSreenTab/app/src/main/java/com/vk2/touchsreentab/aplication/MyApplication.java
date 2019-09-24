package com.vk2.touchsreentab.aplication;

import android.app.Application;

import com.vk2.touchsreentab.api.Api;
import com.vk2.touchsreentab.database.connection.AppDatabase;

public class MyApplication extends Application {
    public static AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        Api.getInstance().create();
        appDatabase = AppDatabase.getAppDatabase(this);
    }
}
