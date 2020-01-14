package com.vk2.touchsreentab.adapter.viewholder;



import androidx.recyclerview.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemHistoryBinding;

public class HistoryViewHolder extends RecyclerView.ViewHolder {
    public ItemHistoryBinding playlistBinding;

    public HistoryViewHolder(ItemHistoryBinding binding) {
        super(binding.getRoot());
        this.playlistBinding = binding;
    }
}
