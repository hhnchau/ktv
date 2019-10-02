package com.vk2.touchsreentab.model.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.vk2.touchsreentab.aplication.MyApplication;
import com.vk2.touchsreentab.database.entity.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlaylistModelView extends ViewModel {
    public static final int ADD = 1;
    public static final int REMOVE = 2;
    public static final int EMPTY = 3;
    public static final int RANDOM = 4;
    public static final int MOVE = 5;
    public static final int PRIORITY = 6;

    private List<Song> lst = new ArrayList<>();
    private MutableLiveData<List<Song>> lstPlaylist = new MutableLiveData<>();

    public MutableLiveData<List<Song>> getPlaylist() {
        return lstPlaylist;
    }

    public void setValue(Context context, Song song, int type) {
        for (Song s : lst) {
            if (song != null && s.getFileName().equals(song.getFileName())) {
                Toast.makeText(context, "Da ton tai", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        switch (type) {
            case ADD:
                lst.add(song);
                if (song != null)
                    new selected().execute(song.getFileName());
                break;
            case REMOVE:
                lst.remove(song);
                if (song != null)
                    new unSelected().execute(song.getFileName());
                break;
            case EMPTY:
                lst.clear();
                new clearSelected().execute();
                break;
            case RANDOM:
                Collections.shuffle(lst);
                break;
            case MOVE:
                while (lst.indexOf(song) != 0) {
                    int i = lst.indexOf(song);
                    Collections.swap(lst, i, i - 1);
                }
                break;
            case PRIORITY:
                lst.add(0, song);
                break;
        }
        this.lstPlaylist.setValue(lst);
    }

    static class selected extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... fileName) {
            MyApplication.appDatabase.songDao().setSelected(1, fileName[0]);
            return null;
        }
    }

    static class unSelected extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... fileName) {
            MyApplication.appDatabase.songDao().setSelected(0, fileName[0]);
            return null;
        }
    }

    static class clearSelected extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            MyApplication.appDatabase.songDao().clearSelected();
            return null;
        }
    }
}
