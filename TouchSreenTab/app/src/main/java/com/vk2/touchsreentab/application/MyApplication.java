package com.vk2.touchsreentab.application;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.vk2.touchsreentab.api.Api;
import com.vk2.touchsreentab.api.ApiController;
import com.vk2.touchsreentab.api.Host;
import com.vk2.touchsreentab.database.connection.AppDatabase;

public class MyApplication extends Application {
    public static AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        Api.getInstance().create(Host.API_VIETKTV);
        appDatabase = AppDatabase.getAppDatabase(this);
        new clearSelected().execute();
    }

    static class clearSelected extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            appDatabase.songDao().clearSelected();
            return null;
        }
    }
}
