package com.vk2.touchsreentab.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;


@Entity(tableName = "song")
public class Song {
    @PrimaryKey()
    @ColumnInfo(name = "FileName")
    @NonNull
    private String fileName = "0";
    @ColumnInfo(name = "SongName")
    private String songName;
    @ColumnInfo(name = "WordNum")
    private Integer wordNum;
    @ColumnInfo(name = "PyCode")
    private String pyCode;
    @ColumnInfo(name = "Stroke")
    private String stroke;
    @ColumnInfo(name = "SingerName1")
    private String singerName1;
    @ColumnInfo(name = "SingerName2")
    private String singerName2;
    @ColumnInfo(name = "Lang")
    private Integer lang;
    @ColumnInfo(name = "MType")
    private Integer mType;
    @ColumnInfo(name = "yTrack")
    private Integer yTrack;
    @ColumnInfo(name = "bTrack")
    private Integer bTrack;
    @ColumnInfo(name = "yVolur")
    private Integer yVolur;
    @ColumnInfo(name = "bVolume")
    private Integer bVolume;
    @ColumnInfo(name = "SongTypeID")
    private Integer songTypeId;
    @ColumnInfo(name = "NewSong")
    private Integer newSong;
    @ColumnInfo(name = "Address")
    private Integer address;
    @ColumnInfo(name = "SingerID1")
    private Integer singerId1;
    @ColumnInfo(name = "SingerID2")
    private Integer singerID2;
    @ColumnInfo(name = "style")
    private Integer style;
    @ColumnInfo(name = "freq")
    private Integer freq;
    @ColumnInfo(name = "SongNamePinyin")
    private String songNamePinyin;
    @ColumnInfo(name = "Spell")
    private String spell;
    @ColumnInfo(name = "NamePinyin")
    private String namePinyin;

    @Ignore
    private String videoPath;

    public Song() {
    }

    @NonNull
    public String getFileName() {
        return fileName;
    }

    public void setFileName(@NonNull String fileName) {
        this.fileName = fileName;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public Integer getWordNum() {
        return wordNum;
    }

    public void setWordNum(Integer wordNum) {
        this.wordNum = wordNum;
    }

    public String getPyCode() {
        return pyCode;
    }

    public void setPyCode(String pyCode) {
        this.pyCode = pyCode;
    }

    public String getStroke() {
        return stroke;
    }

    public void setStroke(String stroke) {
        this.stroke = stroke;
    }

    public String getSingerName1() {
        return singerName1;
    }

    public void setSingerName1(String singerName1) {
        this.singerName1 = singerName1;
    }

    public String getSingerName2() {
        return singerName2;
    }

    public void setSingerName2(String singerName2) {
        this.singerName2 = singerName2;
    }

    public Integer getLang() {
        return lang;
    }

    public void setLang(Integer lang) {
        this.lang = lang;
    }

    public Integer getMType() {
        return mType;
    }

    public void setMType(Integer mType) {
        this.mType = mType;
    }

    public Integer getYTrack() {
        return yTrack;
    }

    public void setYTrack(Integer yTrack) {
        this.yTrack = yTrack;
    }

    public Integer getBTrack() {
        return bTrack;
    }

    public void setBTrack(Integer bTrack) {
        this.bTrack = bTrack;
    }

    public Integer getYVolur() {
        return yVolur;
    }

    public void setYVolur(Integer yVolur) {
        this.yVolur = yVolur;
    }

    public Integer getBVolume() {
        return bVolume;
    }

    public void setBVolume(Integer bVolume) {
        this.bVolume = bVolume;
    }

    public Integer getSongTypeId() {
        return songTypeId;
    }

    public void setSongTypeId(Integer songTypeId) {
        this.songTypeId = songTypeId;
    }

    public Integer getNewSong() {
        return newSong;
    }

    public void setNewSong(Integer newSong) {
        this.newSong = newSong;
    }

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public Integer getSingerId1() {
        return singerId1;
    }

    public void setSingerId1(Integer singerId1) {
        this.singerId1 = singerId1;
    }

    public Integer getSingerID2() {
        return singerID2;
    }

    public void setSingerID2(Integer singerID2) {
        this.singerID2 = singerID2;
    }

    public Integer getStyle() {
        return style;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }

    public Integer getFreq() {
        return freq;
    }

    public void setFreq(Integer freq) {
        this.freq = freq;
    }

    public String getSongNamePinyin() {
        return songNamePinyin;
    }

    public void setSongNamePinyin(String songNamePinyin) {
        this.songNamePinyin = songNamePinyin;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
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

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    @BindingAdapter("urlSongImage")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(4)))
                .into(view);
    }

    @BindingAdapter({"bind:string", "bind:character"})
    public static void spannable(TextView textView, String string, String character) {
        if (string != null && !string.equals("") && character != null && !character.equals("") && character.length() <= string.length()) {
            SpannableString spannableString = new SpannableString(string);
            int start = string.toLowerCase().indexOf(character.toLowerCase());
            int end = start + character.length();
            if (start > -1)
                spannableString.setSpan(new ForegroundColorSpan(Color.RED), start, end, 0);
            textView.setText(spannableString);
        } else {
            textView.setText(string);
        }
    }

    public static DiffUtil.ItemCallback<Song> DIFF_CALLBACK = new DiffUtil.ItemCallback<Song>() {
        @Override
        public boolean areItemsTheSame(@NonNull Song song, @NonNull Song t1) {
            return song.fileName.equals(t1.fileName);
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
        return song.fileName.equals(this.fileName) && song.songName.equals(this.songName);
    }
}
