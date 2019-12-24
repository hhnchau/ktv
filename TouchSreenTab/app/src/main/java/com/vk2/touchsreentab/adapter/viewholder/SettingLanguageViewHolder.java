package com.vk2.touchsreentab.adapter.viewholder;

import android.support.v7.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemSettingLanguageBinding;

public class SettingLanguageViewHolder extends RecyclerView.ViewHolder {
    public ItemSettingLanguageBinding languageBinding;

    public SettingLanguageViewHolder(ItemSettingLanguageBinding binding) {
        super(binding.getRoot());
        this.languageBinding = binding;
    }
}
