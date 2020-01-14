package com.vk2.touchsreentab.adapter.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemSingerRecyclerviewBinding;

public class SingerRecyclerViewHolder extends RecyclerView.ViewHolder {
    public ItemSingerRecyclerviewBinding recyclerViewBinding;

    public SingerRecyclerViewHolder(ItemSingerRecyclerviewBinding binding) {
        super(binding.getRoot());
        this.recyclerViewBinding = binding;
    }
}
