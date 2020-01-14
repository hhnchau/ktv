package com.vk2.touchsreentab.adapter.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemRecyclerviewBinding;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    public ItemRecyclerviewBinding recyclerViewBinding;

    public RecyclerViewHolder(ItemRecyclerviewBinding binding) {
        super(binding.getRoot());
        this.recyclerViewBinding = binding;
    }
}
