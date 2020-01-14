package com.vk2.touchsreentab.model.viewmodel;

import android.content.Context;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.vk2.touchsreentab.database.dao.SingerDao;
import com.vk2.touchsreentab.database.dao.SongDao;
import com.vk2.touchsreentab.database.entity.Singer;
import com.vk2.touchsreentab.database.entity.Song;
import com.vk2.touchsreentab.model.datasource.SingerDataSourceFactory;


public class SingerVewModel extends ViewModel {
    private static final int LIMIT = 20;
    public LiveData<PagedList<Singer>> listSinger;
    public LiveData<PagedList<Singer>> listSingerOnline;
    public LiveData<PagedList<Song>> listSongBySinger;
    public LiveData<PagedList<Singer>> listSearchSinger;
    public MutableLiveData<String> search = new MutableLiveData<>();
    public MutableLiveData<Integer> idSinger = new MutableLiveData<>();
    private PagedList.Config config;

    public SingerVewModel() {
        config = new PagedList.Config.Builder().setPageSize(LIMIT).build();
    }

    public void getAllSinger(final SingerDao singerDao) {
        listSinger = new LivePagedListBuilder<>(singerDao.getAlLSinger(), config).build();
    }

    public void getAllSongBySinger(final SongDao songDao) {
        listSongBySinger = Transformations.switchMap(idSinger, new Function<Integer, LiveData<PagedList<Song>>>() {
            @Override
            public LiveData<PagedList<Song>> apply(Integer input) {
                return new LivePagedListBuilder<>(songDao.getAlLSongBySinger(input), config).build();
            }
        });
    }


    public void getSearchSinger(final SingerDao singerDao) {
        listSearchSinger = Transformations.switchMap(search, new Function<String, LiveData<PagedList<Singer>>>() {
            @Override
            public LiveData<PagedList<Singer>> apply(String input) {
                if (input == null || input.equals("") || input.equals("%%"))
                    return new LivePagedListBuilder<>(
                            singerDao.getAlLSinger(), config
                    ).build();
                else
                    return new LivePagedListBuilder<>(
                            singerDao.getSingerBySearch(input), config

                    ).build();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public void getAllListSinger(Context context) {
        SingerDataSourceFactory singerDataSource = new SingerDataSourceFactory(context);
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(10).build();
        listSingerOnline = new LivePagedListBuilder(singerDataSource, pagedListConfig).build();
    }
}
