package com.vk2.touchsreentab.adapter.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemSongsBinding;

public class SongViewHolder extends RecyclerView.ViewHolder {
    public ItemSongsBinding songBinding;

    public SongViewHolder(ItemSongsBinding binding) {
        super(binding.getRoot());
        this.songBinding = binding;
    }
}