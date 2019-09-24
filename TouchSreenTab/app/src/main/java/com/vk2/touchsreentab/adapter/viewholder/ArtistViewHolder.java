package com.vk2.touchsreentab.adapter.viewholder;

import android.support.v7.widget.RecyclerView;

import com.vk2.touchsreentab.databinding.ItemArtistsBinding;

public class ArtistViewHolder extends RecyclerView.ViewHolder {
        public ItemArtistsBinding artistBinding;

        public ArtistViewHolder(ItemArtistsBinding binding) {
            super(binding.getRoot());
            this.artistBinding = binding;
        }
    }