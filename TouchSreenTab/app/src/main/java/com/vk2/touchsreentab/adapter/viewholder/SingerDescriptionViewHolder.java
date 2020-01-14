package com.vk2.touchsreentab.adapter.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemDescriptionBinding;

public class SingerDescriptionViewHolder extends RecyclerView.ViewHolder {
    public ItemDescriptionBinding descriptionViewBinding;

    public SingerDescriptionViewHolder(ItemDescriptionBinding binding) {
        super(binding.getRoot());
        this.descriptionViewBinding = binding;
    }
}
