package com.vk2.touchsreentab.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "songInfo", indices = @Index(name = "idx_songInfo", value = {"_id"}))

public class songInfo {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Integer _id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "track")
    private Integer track;
    @ColumnInfo(name = "tpos")
    private String tpos;

    public songInfo(Integer _id, String name, Integer track, String tpos) {
        this._id = _id;
        this.name = name;
        this.track = track;
        this.tpos = tpos;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTrack() {
        return track;
    }

    public void setTrack(Integer track) {
        this.track = track;
    }

    public String getTpos() {
        return tpos;
    }

    public void setTpos(String tpos) {
        this.tpos = tpos;
    }
}
