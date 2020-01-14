package com.vk2.touchsreentab.adapter.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemArtistsBinding;

public class ArtistViewHolder extends RecyclerView.ViewHolder {
        public ItemArtistsBinding artistBinding;

        public ArtistViewHolder(ItemArtistsBinding binding) {
            super(binding.getRoot());
            this.artistBinding = binding;
        }
    }