package com.vk2.touchsreentab.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.adapter.viewholder.DownloadViewHolder;
import com.vk2.touchsreentab.database.entity.Song;
import com.vk2.touchsreentab.databinding.ItemDownloadBinding;

import java.util.List;


public class DownloadAdapter extends RecyclerView.Adapter<DownloadViewHolder> {
    private List<Song> lists;

    public DownloadAdapter(List<Song> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public DownloadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDownloadBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_download, parent, false);
        return new DownloadViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final DownloadViewHolder holder, final int position) {
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

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    public interface OnItemClick {
        void onItemClick(Song song);

        void onRemoveClick(Song song);

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