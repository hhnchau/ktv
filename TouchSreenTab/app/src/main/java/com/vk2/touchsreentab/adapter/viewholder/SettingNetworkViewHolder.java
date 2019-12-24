package com.vk2.touchsreentab.adapter.viewholder;

import android.support.v7.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemSettingNetworkBinding;

public class SettingNetworkViewHolder extends RecyclerView.ViewHolder {
    public ItemSettingNetworkBinding networkBinding;

    public SettingNetworkViewHolder(ItemSettingNetworkBinding binding) {
        super(binding.getRoot());
        this.networkBinding = binding;
    }
}
