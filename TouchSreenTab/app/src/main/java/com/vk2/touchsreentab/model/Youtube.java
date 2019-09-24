package com.vk2.touchsreentab.model;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class Youtube {
    private String image;
    private String songName;

    public Youtube() {
    }

    public Youtube(String image, String songName) {
        this.image = image;
        this.songName = songName;
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

    @BindingAdapter("urlYoutubeImage")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(2)))
                .into(view);
    }
}
