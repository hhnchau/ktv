package com.vk2.touchsreentab.adapter.viewholder;

import android.support.v7.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemSongsBinding;

public class SongViewHolder extends RecyclerView.ViewHolder {
    public ItemSongsBinding songBinding;

    public SongViewHolder(ItemSongsBinding binding) {
        super(binding.getRoot());
        this.songBinding = binding;
    }
}