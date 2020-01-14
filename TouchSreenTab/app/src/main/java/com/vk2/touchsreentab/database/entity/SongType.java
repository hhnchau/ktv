package com.vk2.touchsreentab.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "songtype")
public class SongType {
    @PrimaryKey()
    @ColumnInfo(name = "SongTypeID")
    private Integer songTypeID;
    @ColumnInfo(name = "SongTypeName")
    private String songTypeName;

    public SongType() {
    }

    public Integer getSongTypeID() {
        return songTypeID;
    }

    public void setSongTypeID(Integer songTypeID) {
        this.songTypeID = songTypeID;
    }

    public String getSongTypeName() {
        return songTypeName;
    }

    public void setSongTypeName(String songTypeName) {
        this.songTypeName = songTypeName;
    }
}
