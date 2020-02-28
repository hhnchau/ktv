package com.vk2.touchsreentab.database.entity;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.SerializedName;
import com.vk2.touchsreentab.api.ApiController;
import com.vk2.touchsreentab.api.Callback;
import com.vk2.touchsreentab.download.ImageLoaderTask;
import com.vk2.touchsreentab.model.api.UrlForm;


@Entity(tableName = "song"
        , indices = {@Index(name = "idx_SongName", value = "SongName"),
                @Index(name = "idx_filename", value = "FileName"),
                @Index(name = "idx_lang", value = "Lang"),
                @Index(name = "idx_pycode", value = "PyCode"),
                @Index(name = "idx_singername1", value = "SingerName1"),
                @Index(name = "idx_song_singername2", value = "SingerName2"),
                @Index(name = "idx_song_singerid1", value = "SingerID1"),
                @Index(name = "idx_song_singerid2", value = "SingerID2"),
                @Index(name = "idx_stroke", value = "Stroke"),
                @Index(name = "idx_wordnum", value = "WordNum"),}
        )
public class Song {
    @PrimaryKey()
    @ColumnInfo(name = "FileName")
    @NonNull
    @SerializedName("filename")
    private String fileName = "0";

    @SerializedName("songname")
    @ColumnInfo(name = "SongName")
    private String songName;

    @SerializedName("wordnum")
    @ColumnInfo(name = "WordNum")
    private Integer wordNum;

    @SerializedName("pycode")
    @ColumnInfo(name = "PyCode")
    private String pyCode;

    @SerializedName("stroke")
    @ColumnInfo(name = "Stroke")
    private String stroke;

    @SerializedName("singername1")
    @ColumnInfo(name = "SingerName1")
    private String singerName1;

    @SerializedName("singername2")
    @ColumnInfo(name = "SingerName2")
    private String singerName2;

    @SerializedName("lang")
    @ColumnInfo(name = "Lang")
    private Integer lang;

    @SerializedName("mtype")
    @ColumnInfo(name = "MType")
    private Integer mType;

    @SerializedName("ytrack")
    @ColumnInfo(name = "yTrack")
    private Integer yTrack;

    @SerializedName("btrack")
    @ColumnInfo(name = "bTrack")
    private Integer bTrack;

    @SerializedName("yvolume")
    @ColumnInfo(name = "yVolur")
    private Integer yVolur;

    @SerializedName("bvolume")
    @ColumnInfo(name = "bVolume")
    private Integer bVolume;

    @SerializedName("songtypeid")
    @ColumnInfo(name = "SongTypeID")
    private Integer songTypeId;

    @SerializedName("newsong")
    @ColumnInfo(name = "NewSong")
    private Integer newSong;

    @ColumnInfo(name = "Address")
    private Integer address;

    @SerializedName("singerid1")
    @ColumnInfo(name = "SingerID1")
    private Integer singerId1;

    @SerializedName("singerid2")
    @ColumnInfo(name = "SingerID2")
    private Integer singerID2;

    @ColumnInfo(name = "style")
    private Integer style;

    @ColumnInfo(name = "freq")
    private Integer freq;

    @SerializedName("songnamespell")
    @ColumnInfo(name = "SongNamePinyin")
    private String songNamePinyin;

    @ColumnInfo(name = "Spell")
    private String spell;

    @ColumnInfo(name = "NamePinyin")
    private String namePinyin;

    @ColumnInfo(name = "Selected")
    private Integer selected;

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

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    @Ignore
    private String image = "";

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Ignore
    private String videoPath;

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    @Ignore
    private String audioPath;

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    @BindingAdapter("urlYtImage")
    public static void loadYtImage(ImageView view, String urlImage) {
        Glide.with(view.getContext())
                .load(urlImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(4)))
                .into(view);
    }


    @BindingAdapter("urlSongImage")
    public static void loadImage(final ImageView view, final String fileId) {
        Uri uri = ImageLoaderTask.getImageUri(fileId);
        Glide.with(view.getContext())
                .load(uri)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(4)))
                .into(view);

        if (uri.toString().contains("default.png")) {
            ApiController.getInstance().getUrlSinger(view.getContext(), fileId, new Callback() {
                @Override
                public void response(Object obj) {
                    UrlForm urlForm = (UrlForm) obj;
                    if (urlForm != null && urlForm.getErr() == 0) {
                        String url = urlForm.getData().getUrl();
                        if (url != null && !TextUtils.isEmpty(url)) {
                            new ImageLoaderTask(view.getContext(), fileId, new ImageLoaderTask.Callback() {
                                @Override
                                public void onBitmap(Bitmap bitmap) {
                                    Glide.with(view.getContext())
                                            .load(bitmap)
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(4)))
                                            .into(view);
                                }
                            }).execute(url);
                        }
                    }
                }
            });
        }
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
        public boolean areItemsTheSame(@NonNull Song song, @NonNull Song song1) {
            return song.fileName.equals(song1.fileName);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Song song, @NonNull Song song1) {
            return song.equals(song1);
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        Song song = (Song) obj;
        return song.selected.equals(this.selected);
    }
}
