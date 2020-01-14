package com.vk2.touchsreentab.adapter.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemDownloadBinding;

public class DownloadViewHolder extends RecyclerView.ViewHolder {
    public ItemDownloadBinding playlistBinding;

    public DownloadViewHolder(ItemDownloadBinding binding) {
        super(binding.getRoot());
        this.playlistBinding = binding;
    }
}
