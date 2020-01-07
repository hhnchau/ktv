package com.vk2.touchsreentab.adapter.viewholder;

import android.support.v7.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemCheckboxBinding;

public class CheckboxViewHolder extends RecyclerView.ViewHolder {
    public ItemCheckboxBinding checkboxBinding;

    public CheckboxViewHolder(ItemCheckboxBinding binding) {
        super(binding.getRoot());
        this.checkboxBinding = binding;
    }
}