package com.vk2.touchsreentab.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;
import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class Song {
    @ColumnInfo(name = "_id")
    private int id;
    @Ignore
    private String image = "https://api.androidhive.info/images/nature/1.jpg";
    @ColumnInfo(name = "nameSong")
    private String songName;
    @ColumnInfo(name = "nameSinger")
    private String singerName;
    @Ignore
    private int type;
    private  String videoPath;

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public Song() {
    }

    public Song(String image, String songName, String singerName, int type) {
        this.image = image;
        this.songName = songName;
        this.singerName = singerName;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @BindingAdapter("urlSongImage")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(4)))
                .into(view);
    }


    public static DiffUtil.ItemCallback<Song> DIFF_CALLBACK = new DiffUtil.ItemCallback<Song>() {
        @Override
        public boolean areItemsTheSame(@NonNull Song song, @NonNull Song t1) {
            return song.id == t1.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Song song, @NonNull Song t1) {
            return song.equals(t1);
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        Song song = (Song) obj;
        return song.id == this.id && song.songName.equals(this.songName);
    }
}
