package com.vk2.touchsreentab.database.dao;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.vk2.touchsreentab.database.entity.Song;

@Dao
public interface SongDao {
    @Query("SELECT * FROM song")
    DataSource.Factory<Integer, Song> getAlLSong();

    @Query("SELECT * FROM song WHERE SongNamePinyin LIKE :keyword OR PyCode LIKE :keyword")
    DataSource.Factory<Integer, Song> getSongBySearch(String keyword);
}
