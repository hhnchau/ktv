package com.vk2.touchsreentab.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.vk2.touchsreentab.model.Category;

import java.util.List;

import io.reactivex.Flowable;


@Dao
public interface CategoryDao {
    @Query("SELECT * FROM songlang")
    Flowable<List<Category>> getAlLSongLang();

    @Query("SELECT * FROM songtype")
    Flowable<List<Category>> getAlLSongType();
}
