package com.vk2.touchsreentab.adapter.viewholder;
import androidx.recyclerview.widget.RecyclerView;
import com.vk2.touchsreentab.databinding.ItemCategoryRecyclerviewBinding;

public class CategoryRecyclerViewHolder extends RecyclerView.ViewHolder {
    public ItemCategoryRecyclerviewBinding recyclerViewBinding;

    public CategoryRecyclerViewHolder(ItemCategoryRecyclerviewBinding binding) {
        super(binding.getRoot());
        this.recyclerViewBinding = binding;
    }
}
