package com.vk2.touchsreentab.database.dao;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.vk2.touchsreentab.database.entity.Singer;

@Dao
public interface SingerDao {
    @Query("SELECT * FROM singer")
    DataSource.Factory<Integer, Singer> getAlLSinger();

    @Query("SELECT * FROM singer WHERE NamePinyin LIKE '%'+:keyword+'%' OR Spell LIKE :keyword")
    DataSource.Factory<Integer, Singer> getSingerBySearch(String keyword);
}
