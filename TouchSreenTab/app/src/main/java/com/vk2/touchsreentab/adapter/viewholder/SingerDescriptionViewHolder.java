package com.vk2.touchsreentab.adapter.viewholder;

import android.support.v7.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemDescriptionBinding;
import com.vk2.touchsreentab.databinding.ItemRecyclerviewBinding;

public class SingerDescriptionViewHolder extends RecyclerView.ViewHolder {
    public ItemDescriptionBinding descriptionViewBinding;

    public SingerDescriptionViewHolder(ItemDescriptionBinding binding) {
        super(binding.getRoot());
        this.descriptionViewBinding = binding;
    }
}
