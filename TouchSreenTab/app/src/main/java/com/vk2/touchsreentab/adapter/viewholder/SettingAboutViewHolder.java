package com.vk2.touchsreentab.adapter.viewholder;

import android.support.v7.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemSettingAboutBinding;
import com.vk2.touchsreentab.databinding.ItemSettingMusicBinding;

public class SettingAboutViewHolder extends RecyclerView.ViewHolder {
    public ItemSettingAboutBinding binding;

    public SettingAboutViewHolder(ItemSettingAboutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
