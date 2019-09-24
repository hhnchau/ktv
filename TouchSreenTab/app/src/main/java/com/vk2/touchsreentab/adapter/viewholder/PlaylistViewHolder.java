package com.vk2.touchsreentab.adapter.viewholder;

import android.support.v7.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemPlaylistBinding;
import com.vk2.touchsreentab.databinding.ItemSongsBinding;

public class PlaylistViewHolder extends RecyclerView.ViewHolder {
    public ItemPlaylistBinding playlistBinding;

    public PlaylistViewHolder(ItemPlaylistBinding binding) {
        super(binding.getRoot());
        this.playlistBinding = binding;
    }
}
