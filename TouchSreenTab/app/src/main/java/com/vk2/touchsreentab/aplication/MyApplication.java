package com.vk2.touchsreentab.aplication;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

import com.vk2.touchsreentab.api.Api;
import com.vk2.touchsreentab.api.Host;
import com.vk2.touchsreentab.database.connection.AppDatabase;
import com.vk2.touchsreentab.model.viewmodel.SongViewModel;

public class MyApplication extends Application {
    public static AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        Api.getInstance().create(Host.URL);
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
