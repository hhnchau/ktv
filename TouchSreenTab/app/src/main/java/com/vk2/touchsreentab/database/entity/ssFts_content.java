package com.vk2.touchsreentab.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "ssFts_content")
public class ssFts_content {
    @PrimaryKey()
    @ColumnInfo(name = "docid")
    private Integer docid;
    @ColumnInfo(name = "c0_id")
    private Integer c0_id;
    @ColumnInfo(name = "c1sid1")
    private Integer c1sid1;
    @ColumnInfo(name = "c2sid2")
    private Integer c2sid2;

    public ssFts_content(Integer docid, Integer c0_id, Integer c1sid1, Integer c2sid2) {
        this.docid = docid;
        this.c0_id = c0_id;
        this.c1sid1 = c1sid1;
        this.c2sid2 = c2sid2;
    }

    public Integer getDocid() {
        return docid;
    }

    public void setDocid(Integer docid) {
        this.docid = docid;
    }

    public Integer getC0_id() {
        return c0_id;
    }

    public void setC0_id(Integer c0_id) {
        this.c0_id = c0_id;
    }

    public Integer getC1sid1() {
        return c1sid1;
    }

    public void setC1sid1(Integer c1sid1) {
        this.c1sid1 = c1sid1;
    }

    public Integer getC2sid2() {
        return c2sid2;
    }

    public void setC2sid2(Integer c2sid2) {
        this.c2sid2 = c2sid2;
    }
}
