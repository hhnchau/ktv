package com.vk2.touchsreentab.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "songlang")
public class SongLang {
    @PrimaryKey()
    @ColumnInfo(name = "LangNum")
    private Integer langNum;
    @ColumnInfo(name = "LangDis")
    private String langDis;

    public SongLang() {
    }

    public Integer getLangNum() {
        return langNum;
    }

    public void setLangNum(Integer langNum) {
        this.langNum = langNum;
    }

    public String getLangDis() {
        return langDis;
    }

    public void setLangDis(String langDis) {
        this.langDis = langDis;
    }
}
