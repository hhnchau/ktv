package com.vk2.touchsreentab.model.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

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

    public void setValue(Song song, int type) {
        switch (type) {
            case ADD:
                lst.add(song);
                break;
            case REMOVE:
                lst.remove(song);
                break;
            case EMPTY:
                lst.clear();
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
}
