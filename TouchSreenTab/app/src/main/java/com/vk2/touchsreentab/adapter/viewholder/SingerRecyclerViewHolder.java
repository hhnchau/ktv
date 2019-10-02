package com.vk2.touchsreentab.adapter.viewholder;

import android.support.v7.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemSingerRecyclerviewBinding;

public class SingerRecyclerViewHolder extends RecyclerView.ViewHolder {
    public ItemSingerRecyclerviewBinding recyclerViewBinding;

    public SingerRecyclerViewHolder(ItemSingerRecyclerviewBinding binding) {
        super(binding.getRoot());
        this.recyclerViewBinding = binding;
    }
}
