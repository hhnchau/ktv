package com.vk2.touchsreentab.adapter.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemStickyHeaderBinding;

public class StickyHeaderViewHolder extends RecyclerView.ViewHolder {
    public ItemStickyHeaderBinding stickyBinding;

    public StickyHeaderViewHolder(ItemStickyHeaderBinding binding) {
        super(binding.getRoot());
        this.stickyBinding = binding;
    }
}
