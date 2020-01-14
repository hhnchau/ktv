package com.vk2.touchsreentab.adapter.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemSettingLanguageBinding;

public class SettingLanguageViewHolder extends RecyclerView.ViewHolder {
    public ItemSettingLanguageBinding languageBinding;

    public SettingLanguageViewHolder(ItemSettingLanguageBinding binding) {
        super(binding.getRoot());
        this.languageBinding = binding;
    }
}
