package com.vk2.touchsreentab.adapter.viewholder;

import android.support.v7.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemCategoryRecyclerviewBinding;

public class CategoryRecyclerViewHolder extends RecyclerView.ViewHolder {
    public ItemCategoryRecyclerviewBinding recyclerViewBinding;

    public CategoryRecyclerViewHolder(ItemCategoryRecyclerviewBinding binding) {
        super(binding.getRoot());
        this.recyclerViewBinding = binding;
    }
}
