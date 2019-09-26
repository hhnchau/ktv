package com.vk2.touchsreentab.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.databinding.ItemSoundcloudBinding;
import com.vk2.touchsreentab.model.Song;
import com.vk2.touchsreentab.model.SoundCloud;

import java.util.List;

public class SoundCloudAdapter extends RecyclerView.Adapter<SoundCloudAdapter.MyViewHolder> {
    private List<Song> lists;

    public SoundCloudAdapter(List<Song> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSoundcloudBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_soundcloud, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Song soundCloud = lists.get(holder.getAdapterPosition());
        if (soundCloud == null) return;
        holder.binding.setSoundCloud(soundCloud);

        holder.binding.song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClick != null) onItemClick.onItemClick(soundCloud);
            }
        });

        holder.binding.imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClick != null) onItemClick.onIconClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemSoundcloudBinding binding;

        MyViewHolder(ItemSoundcloudBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    public interface OnItemClick {
        void onItemClick(Song soundCloud);

        void onIconClick();
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

}