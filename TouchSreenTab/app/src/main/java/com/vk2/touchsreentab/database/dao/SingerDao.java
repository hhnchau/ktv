package com.vk2.touchsreentab.database.dao;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Query;

import com.vk2.touchsreentab.database.entity.Singer;

@Dao
public interface SingerDao {
    @Query("SELECT * FROM singer")
    DataSource.Factory<Integer, Singer> getAlLSinger();

    @Query("SELECT * FROM singer WHERE NamePinyin LIKE '%'+:keyword+'%' OR Spell LIKE :keyword")
    DataSource.Factory<Integer, Singer> getSingerBySearch(String keyword);
}
