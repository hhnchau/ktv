package com.vk2.touchsreentab.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "songrankn", indices = {@Index(name = "idx_songrankn", value = {"_id", "hits"})})
public class songrankn {
    @PrimaryKey()
    @ColumnInfo(name = "_id")
    private Integer _id;
    @ColumnInfo(name = "hits")
    private Integer hits;

    public songrankn(Integer _id, Integer hits) {
        this._id = _id;
        this.hits = hits;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }
}
