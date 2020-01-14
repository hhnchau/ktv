package com.vk2.touchsreentab.adapter.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemSettingSongManagementBinding;

public class SettingSongManagementViewHolder extends RecyclerView.ViewHolder {
    public ItemSettingSongManagementBinding songBinding;

    public SettingSongManagementViewHolder(ItemSettingSongManagementBinding binding) {
        super(binding.getRoot());
        this.songBinding = binding;
    }
}
