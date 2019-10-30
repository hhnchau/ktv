package com.vk2.touchsreentab.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.adapter.viewholder.PlaylistViewHolder;
import com.vk2.touchsreentab.database.entity.Song;
import com.vk2.touchsreentab.databinding.ItemPlaylistBinding;

import java.util.List;


public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistViewHolder> {
    private List<Song> lists;

    public PlaylistAdapter(List<Song> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPlaylistBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_playlist, parent, false);
        return new PlaylistViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlaylistViewHolder holder, final int position) {
        final Song song = lists.get(holder.getAdapterPosition());
        if (song == null) return;
        holder.playlistBinding.setSong(song);
        holder.playlistBinding.setNumber(String.valueOf(position+1));

        holder.playlistBinding.playList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClick != null) onItemClick.onItemClick(song);
            }
        });
        holder.playlistBinding.imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClick != null) onItemClick.onRemoveClick(song);
            }
        });
        holder.playlistBinding.imgMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClick != null) onItemClick.onMoveClick(song);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    public interface OnItemClick {
        void onItemClick(Song song);

        void onRemoveClick(Song song);

        void onMoveClick(Song song);


    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void update(List<Song> songs) {
        lists.clear();
        lists.addAll(songs);
        notifyDataSetChanged();
    }

}