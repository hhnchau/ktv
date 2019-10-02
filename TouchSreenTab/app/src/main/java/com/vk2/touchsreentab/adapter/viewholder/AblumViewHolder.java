package com.vk2.touchsreentab.adapter.viewholder;

import android.support.v7.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemAlbumsBinding;


public class AblumViewHolder extends RecyclerView.ViewHolder {
    public ItemAlbumsBinding albumsBinding;

    public AblumViewHolder(ItemAlbumsBinding binding) {
        super(binding.getRoot());
        this.albumsBinding = binding;
    }
}