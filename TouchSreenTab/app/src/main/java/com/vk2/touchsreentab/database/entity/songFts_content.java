package com.vk2.touchsreentab.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;

public class songFts_content {
    @PrimaryKey()
    @ColumnInfo(name = "docid")
    private Integer docid;
    @ColumnInfo(name = "c0_id")
    private Integer c0_id;
    @ColumnInfo(name = "c1sid1")
    private Integer c1sid1;
    @ColumnInfo(name = "c2sid2")
    private Integer c2sid2;
}
