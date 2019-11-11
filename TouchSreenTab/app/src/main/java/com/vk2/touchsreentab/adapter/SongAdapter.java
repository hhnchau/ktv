package com.vk2.touchsreentab.adapter;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.adapter.viewholder.SongViewHolder;
import com.vk2.touchsreentab.database.entity.Song;
import com.vk2.touchsreentab.databinding.ItemSongsBinding;
import com.vk2.touchsreentab.utils.OnSingleClickListener;


public class SongAdapter extends PagedListAdapter<Song, SongViewHolder> {
    public SongAdapter() {
        super(Song.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSongsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_songs, parent, false);
        return new SongViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final SongViewHolder songViewHolder, int i) {
        final Song song = getItem(i);
        if (song == null) return;
        songViewHolder.songBinding.setSong(song);

        songViewHolder.songBinding.imgSong.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (onItemClick != null) onItemClick.onImageClick(song);
                enable();
            }
        });

        songViewHolder.songBinding.song.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (onItemClick != null) onItemClick.onItemClick(song);
                enable();
            }
        });

        songViewHolder.songBinding.imgMore.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (onItemClick != null) onItemClick.onIconClick(song);
                enable();
            }
        });
        songViewHolder.songBinding.executePendingBindings();
    }

    public interface OnItemClick {
        void onImageClick(Song song);

        void onItemClick(Song song);

        void onIconClick(Song song);
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

}