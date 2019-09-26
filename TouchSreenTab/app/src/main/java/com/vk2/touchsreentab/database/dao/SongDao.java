package com.vk2.touchsreentab.database.dao;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.vk2.touchsreentab.database.entity.Song;

@Dao
public interface SongDao {
    @Query("SELECT * FROM song")
    DataSource.Factory<Integer, Song> getAlLSong();

    @Query("SELECT * FROM (SELECT * FROM song LIMIT 1) UNION ALL SELECT * FROM song WHERE SongNamePinyin LIKE '%'+:keyword +'%' OR PyCode LIKE :keyword LIMIT 300")
    DataSource.Factory<Integer, Song> getSongBySearch(String keyword);
}
