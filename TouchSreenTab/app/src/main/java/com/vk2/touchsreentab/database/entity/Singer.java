package com.vk2.touchsreentab.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

@Entity(tableName = "singer")
public class Singer {
    @PrimaryKey()
    @ColumnInfo(name = "ID")
    private Integer id;
    @ColumnInfo(name = "Name")
    private String name;
    @ColumnInfo(name = "Sex")
    private Integer sex;
    @ColumnInfo(name = "Lang")
    private Integer lang;
    @ColumnInfo(name = "Spell")
    private String spell;
    @ColumnInfo(name = "FileName")
    private String fileName;
    @ColumnInfo(name = "Star")
    private Integer star;
    @ColumnInfo(name = "NamePinyin")
    private String namePinyin;

    public Singer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getLang() {
        return lang;
    }

    public void setLang(Integer lang) {
        this.lang = lang;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getNamePinyin() {
        return namePinyin;
    }

    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
    }

    @Ignore
    private String image = "https://api.androidhive.info/images/nature/2.jpg";

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @BindingAdapter("urlSingerImage")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(view);
    }

    public static DiffUtil.ItemCallback<Singer> DIFF_CALLBACK = new DiffUtil.ItemCallback<Singer>() {
        @Override
        public boolean areItemsTheSame(@NonNull Singer singer, @NonNull Singer singer1) {
            return singer.id.equals(singer1.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Singer singer, @NonNull Singer singer1) {
            return singer.equals(singer1);
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        Singer singer = (Singer) obj;
        return singer.fileName.equals(this.fileName);
    }
}
