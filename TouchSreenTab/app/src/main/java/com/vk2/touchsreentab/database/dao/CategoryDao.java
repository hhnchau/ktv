package com.vk2.touchsreentab.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

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
