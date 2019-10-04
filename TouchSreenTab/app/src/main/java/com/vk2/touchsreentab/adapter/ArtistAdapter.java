package com.vk2.touchsreentab.adapter;

import android.arch.paging.PagedListAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.adapter.viewholder.ArtistViewHolder;
import com.vk2.touchsreentab.database.entity.Singer;
import com.vk2.touchsreentab.databinding.ItemArtistsBinding;

public class ArtistAdapter extends PagedListAdapter<Singer, ArtistViewHolder> {

    public ArtistAdapter() {
        super(Singer.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemArtistsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_artists, parent, false);
        return new ArtistViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder artistViewHolder, int i) {
        final Singer singer = getItem(i);
        if (singer == null) return;
        artistViewHolder.artistBinding.setSinger(singer);

        artistViewHolder.artistBinding.artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClick != null) onItemClick.onClick(singer);
            }
        });
        artistViewHolder.artistBinding.executePendingBindings();
    }

   public interface OnItemClick {
        void onClick(Singer artist);
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

}