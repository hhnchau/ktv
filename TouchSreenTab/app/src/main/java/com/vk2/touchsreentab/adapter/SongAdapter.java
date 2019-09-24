package com.vk2.touchsreentab.adapter;

import android.arch.paging.PagedListAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.adapter.viewholder.SongViewHolder;
import com.vk2.touchsreentab.database.entity.Song;
import com.vk2.touchsreentab.databinding.ItemSongsBinding;


public class SongAdapter extends PagedListAdapter<Song, SongViewHolder> {
    private boolean hasImage;

    public SongAdapter(boolean hasImage) {
        super(Song.DIFF_CALLBACK);
        this.hasImage = hasImage;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSongsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_songs, parent, false);
        return new SongViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder songViewHolder, int i) {
        final Song song = getItem(i);
        if (song == null) return;
        songViewHolder.songBinding.setHasImage(hasImage);
        songViewHolder.songBinding.setSong(song);
    }

    public interface OnItemClick {
        void onItemClick(Song song);

        void onIconClick();
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

}