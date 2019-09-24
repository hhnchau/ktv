package com.vk2.touchsreentab.model;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class SoundCloud {
    private String image;
    private String songName;
    private String singerName;

    public SoundCloud() {
    }

    public SoundCloud(String image, String songName, String singerName) {
        this.image = image;
        this.songName = songName;
        this.singerName = singerName;
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

    @BindingAdapter("urlSoundCloudImage")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(4)))
                .into(view);
    }
}
