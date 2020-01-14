package com.vk2.touchsreentab.adapter.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemSongRecyclerviewBinding;

public class SongRecyclerViewHolder extends RecyclerView.ViewHolder {
    public ItemSongRecyclerviewBinding recyclerViewBinding;

    public SongRecyclerViewHolder(ItemSongRecyclerviewBinding binding) {
        super(binding.getRoot());
        this.recyclerViewBinding = binding;
    }
}
