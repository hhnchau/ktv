package com.vk2.touchsreentab.adapter.viewholder;

import android.support.v7.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemStickyHeaderBinding;

public class StickyHeaderViewHolder extends RecyclerView.ViewHolder {
    public ItemStickyHeaderBinding stickyBinding;

    public StickyHeaderViewHolder(ItemStickyHeaderBinding binding) {
        super(binding.getRoot());
        this.stickyBinding = binding;
    }
}
