package com.vk2.touchsreentab.adapter.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemButtonBinding;

public class ButtonViewHolder  extends RecyclerView.ViewHolder  {
    public ItemButtonBinding buttonBinding;

    public ButtonViewHolder(ItemButtonBinding binding) {
        super(binding.getRoot());
        this.buttonBinding = binding;
    }
}
