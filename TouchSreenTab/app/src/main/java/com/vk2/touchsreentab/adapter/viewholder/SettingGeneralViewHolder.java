package com.vk2.touchsreentab.adapter.viewholder;

import android.support.v7.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemSettingGeneralBinding;

public class SettingGeneralViewHolder extends RecyclerView.ViewHolder {
    public ItemSettingGeneralBinding generalBinding;

    public SettingGeneralViewHolder(ItemSettingGeneralBinding binding) {
        super(binding.getRoot());
        this.generalBinding = binding;
    }
}