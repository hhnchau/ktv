package com.vk2.touchsreentab.adapter.viewholder;
import androidx.recyclerview.widget.RecyclerView;
import com.vk2.touchsreentab.databinding.ItemSettingMenuBinding;

public class SettingMenuViewHolder extends RecyclerView.ViewHolder {
    public ItemSettingMenuBinding menuBinding;

    public SettingMenuViewHolder(ItemSettingMenuBinding binding) {
        super(binding.getRoot());
        this.menuBinding = binding;
    }
}
