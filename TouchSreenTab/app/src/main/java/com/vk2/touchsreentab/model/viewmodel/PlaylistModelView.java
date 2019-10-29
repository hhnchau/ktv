package com.vk2.touchsreentab.model.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.vk2.touchsreentab.application.MyApplication;
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
    public static final int PLAYED = 7;

    private List<Song> cachePlaylist = new ArrayList<>();
    private MutableLiveData<List<Song>> lstPlaylist = new MutableLiveData<>();
    private List<Song> cacheHistory = new ArrayList<>();
    private MutableLiveData<List<Song>> lstHistory = new MutableLiveData<>();

    public MutableLiveData<List<Song>> getPlaylist() {
        return lstPlaylist;
    }

    public MutableLiveData<List<Song>> getHistory() {
        return lstHistory;
    }

    public void setValue(Context context, Song song, int type) {
        song.setSongName("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
        if (type == ADD)
            for (Song s : cachePlaylist) {
                if (song != null && s.getFileName().equals(song.getFileName())) {
                    Toast.makeText(context, "Da ton tai", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

        switch (type) {
            case ADD:
                cachePlaylist.add(song);
                if (song != null)
                    new selected().execute(song.getFileName());
                break;
            case REMOVE:
                cachePlaylist.remove(song);
                setHistory(song);
                if (song != null)
                    new unSelected().execute(song.getFileName());
                break;
            case PLAYED:
                cachePlaylist.remove(song);
                setHistory(song);
                if (song != null)
                    new unSelected().execute(song.getFileName());
                break;
            case EMPTY:
                cachePlaylist.clear();
                new clearSelected().execute();
                break;
            case RANDOM:
                Collections.shuffle(cachePlaylist);
                break;
            case MOVE:
                for (int i = 0; i < cachePlaylist.size(); i++) {
                    if (song != null && cachePlaylist.get(i).getFileName().equals(song.getFileName())) {
                        cachePlaylist.remove(i);
                        cachePlaylist.add(0, song);
                        break;
                    }
                }
                break;
            case PRIORITY:
                cachePlaylist.add(0, song);
                break;
        }
        this.lstPlaylist.setValue(cachePlaylist);
    }

    private void setHistory(Song song) {
        cacheHistory.add(0, song);
        this.lstHistory.setValue(cacheHistory);
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
