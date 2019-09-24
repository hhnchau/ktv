package com.vk2.touchsreentab.adapter.viewholder;

import android.support.v7.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemRecyclerviewBinding;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    public ItemRecyclerviewBinding recyclerViewBinding;

    public RecyclerViewHolder(ItemRecyclerviewBinding binding) {
        super(binding.getRoot());
        this.recyclerViewBinding = binding;
    }
}
