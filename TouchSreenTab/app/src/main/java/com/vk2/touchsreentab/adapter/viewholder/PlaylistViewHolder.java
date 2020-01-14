package com.vk2.touchsreentab.adapter.viewholder;
import androidx.recyclerview.widget.RecyclerView;
import com.vk2.touchsreentab.databinding.ItemPlaylistBinding;

public class PlaylistViewHolder extends RecyclerView.ViewHolder {
    public ItemPlaylistBinding playlistBinding;

    public PlaylistViewHolder(ItemPlaylistBinding binding) {
        super(binding.getRoot());
        this.playlistBinding = binding;
    }
}
