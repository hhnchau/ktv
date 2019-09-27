package com.vk2.touchsreentab.model.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.vk2.touchsreentab.database.dao.SongDao;
import com.vk2.touchsreentab.database.entity.Song;


public class SongViewModel extends ViewModel {
    public LiveData<PagedList<Song>> listSong;
    public LiveData<PagedList<Song>> listSearchSong;
    public MutableLiveData<String> search = new MutableLiveData<>();
    private PagedList.Config config;

    private static final int LIMIT = 20;

    public SongViewModel() {
        config = new PagedList.Config.Builder().setPageSize(LIMIT).build();
    }

    public void getAllSong(final SongDao songDao) {
        listSong = new LivePagedListBuilder<>(songDao.getAlLSong(), config).build();
    }

    public void getSearchSong(final SongDao songDao) {
        listSearchSong = Transformations.switchMap(search, new Function<String, LiveData<PagedList<Song>>>() {
            @Override
            public LiveData<PagedList<Song>> apply(String input) {
                if (input == null || input.equals("") || input.equals("%%"))
                    return new LivePagedListBuilder<>(
                            songDao.getAlLSong(), config
                    ).build();
                else
                    return new LivePagedListBuilder<>(
                            songDao.getSongBySearch("%" + input + "%", input), config
                    ).build();
            }
        });
    }
}
