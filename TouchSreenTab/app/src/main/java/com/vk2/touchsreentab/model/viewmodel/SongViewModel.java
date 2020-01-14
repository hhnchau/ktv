package com.vk2.touchsreentab.model.viewmodel;

import android.content.Context;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.vk2.touchsreentab.database.dao.SongDao;
import com.vk2.touchsreentab.database.entity.Song;
import com.vk2.touchsreentab.model.datasource.SongDataSource;
import com.vk2.touchsreentab.model.datasource.SongDataSourceFactory;


public class SongViewModel extends ViewModel {
    public LiveData<PagedList<Song>> listSong;
    public LiveData<PagedList<Song>> listSearchSong;
    public MutableLiveData<String> search = new MutableLiveData<>();
    private PagedList.Config config;
    public LiveData<PagedList<Song>> listSongOnline;

    public LiveData<Boolean> progressLoadStatus = new MutableLiveData<>();

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

    @SuppressWarnings("unchecked")
    public void getAllListSong(Context context) {
        SongDataSourceFactory songDataSourceFactory = new SongDataSourceFactory(context);
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(10).build();
        listSongOnline = new LivePagedListBuilder(songDataSourceFactory, pagedListConfig).build();
        progressLoadStatus = Transformations.switchMap(songDataSourceFactory.getMutableLiveData(), new Function<SongDataSource, LiveData<Boolean>>() {
            @Override
            public LiveData<Boolean> apply(SongDataSource progressLoadStatus) {
                return progressLoadStatus.progressLiveStatus;
            }
        });
    }
}
