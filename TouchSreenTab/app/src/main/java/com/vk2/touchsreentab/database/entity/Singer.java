package com.vk2.touchsreentab.database.entity;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.SerializedName;
import com.vk2.touchsreentab.api.ApiController;
import com.vk2.touchsreentab.api.Callback;
import com.vk2.touchsreentab.download.ImageLoaderTask;
import com.vk2.touchsreentab.model.api.Url;
import com.vk2.touchsreentab.model.api.UrlForm;

@Entity(tableName = "singer", indices = {@Index(name = "idx_singer_id", value = "ID"), @Index(name = "idx_singer_lang", value = "Lang"), @Index(name = "idx_singer_name", value = "Name"), @Index(name = "idx_singer_spell", value = "Spell")})
public class Singer {
    @PrimaryKey()
    @ColumnInfo(name = "ID")
    private Integer id;

    @ColumnInfo(name = "Name")
    private String name;

    @SerializedName("sex_id")
    @ColumnInfo(name = "Sex")
    private Integer sex;

    @SerializedName("lang_id")
    @ColumnInfo(name = "Lang")
    private Integer lang;

    @SerializedName("singerNameSpell")
    @ColumnInfo(name = "Spell")
    private String spell;

    @SerializedName("fileId")
    @ColumnInfo(name = "FileName")
    private String fileName;

    @ColumnInfo(name = "Star")
    private Integer star;

    @SerializedName("pinyin")
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

    @BindingAdapter("urlSingerImage")
    public static void loadImage(final ImageView view, final String fileId) {
        Uri uri = ImageLoaderTask.getImageUri(fileId);
        Glide.with(view.getContext())
                .load(uri)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.circleCropTransform())
                .into(view);
        if (uri.toString().contains("default.png")) {

            ApiController.getInstance().getUrlSinger(view.getContext(), fileId, new Callback() {
                @Override
                public void response(Object obj) {
                    UrlForm urlForm = (UrlForm) obj;
                    if (urlForm != null && urlForm.getErr() == 0){
                        String url = urlForm.getData().getUrl();
                        if (url != null && !TextUtils.isEmpty(url)){
                            new ImageLoaderTask(view.getContext(), fileId, new ImageLoaderTask.Callback() {
                                @Override
                                public void onBitmap(Bitmap bitmap) {
                                    Glide.with(view.getContext())
                                            .load(bitmap)
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .apply(RequestOptions.circleCropTransform())
                                            .into(view);
                                }
                            }).execute(url);
                        }
                    }
                }
            });
        }
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
