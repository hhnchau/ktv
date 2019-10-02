package com.vk2.touchsreentab.adapter.viewholder;

import android.support.v7.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemHistoryBinding;

public class HistoryViewHolder extends RecyclerView.ViewHolder {
    public ItemHistoryBinding playlistBinding;

    public HistoryViewHolder(ItemHistoryBinding binding) {
        super(binding.getRoot());
        this.playlistBinding = binding;
    }
}
