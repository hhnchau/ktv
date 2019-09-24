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
    public MutableLiveData<String> search = new MutableLiveData<>();

    private static final int LIMIT = 20;

    public SongViewModel() {
    }


    public void getSongData(final SongDao songDao) {
        final PagedList.Config config = new PagedList.Config.Builder().setPageSize(LIMIT).build();

        listSong = Transformations.switchMap(search, new Function<String, LiveData<PagedList<Song>>>() {
            @Override
            public LiveData<PagedList<Song>> apply(String input) {
                if (input == null || input.equals("") || input.equals("%%"))
                    return new LivePagedListBuilder<>(
                            songDao.getAlLSong(), config
                    ).build();
                else
                    return new LivePagedListBuilder<>(
                            songDao.getSongBySearch(input), config
                    ).build();
            }
        });
    }
}
