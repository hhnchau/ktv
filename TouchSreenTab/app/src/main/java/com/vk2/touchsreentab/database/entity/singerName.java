package com.vk2.touchsreentab.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "singerName", indices = @Index(name = "idx_singerName", value = {"_id", "singerId", "name"}))
public class singerName {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Integer _id;
    @ColumnInfo(name = "singerId")
    private Integer singerId;
    @ColumnInfo(name = "name")
    private String name;


    public singerName(Integer _id, Integer singerId, String name) {
        this._id = _id;
        this.singerId = singerId;
        this.name = name;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public Integer getSingerId() {
        return singerId;
    }

    public void setSingerId(Integer singerId) {
        this.singerId = singerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
