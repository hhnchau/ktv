package com.vk2.touchsreentab.adapter.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemSettingMusicBinding;

public class SettingMusicViewHolder extends RecyclerView.ViewHolder {
    public ItemSettingMusicBinding musicBinding;

    public SettingMusicViewHolder(ItemSettingMusicBinding binding) {
        super(binding.getRoot());
        this.musicBinding = binding;
    }
}
