package com.vk2.touchsreentab.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;

public class Category implements Comparable, Cloneable  {
    @ColumnInfo(name = "SongTypeID")
    private Integer songTypeID;
    @ColumnInfo(name = "SongTypeName")
    private String songTypeName;
    @ColumnInfo(name = "LangNum")
    private Integer langNum;
    @ColumnInfo(name = "LangDis")
    private String langDis;
    @Ignore
    private boolean selected;

    public Category() {
    }

    @Override
    public int compareTo(Object o) {
        Category category = (Category) o;

        if (category.selected == this.selected) {
            return 0;
        }
        return 1;
    }

    @Override
    public Category clone() {
        Category category;

        try {
            category = (Category) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return category;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
