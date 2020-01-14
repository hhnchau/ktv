package com.vk2.touchsreentab.adapter.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemSettingGeneralBinding;

public class SettingGeneralViewHolder extends RecyclerView.ViewHolder {
    public ItemSettingGeneralBinding generalBinding;

    public SettingGeneralViewHolder(ItemSettingGeneralBinding binding) {
        super(binding.getRoot());
        this.generalBinding = binding;
    }
}