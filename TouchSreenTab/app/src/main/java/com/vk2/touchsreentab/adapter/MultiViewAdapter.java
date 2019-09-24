package com.vk2.touchsreentab.adapter;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.adapter.viewholder.RecyclerViewHolder;
import com.vk2.touchsreentab.adapter.viewholder.SongViewHolder;

import com.vk2.touchsreentab.aplication.MyApplication;
import com.vk2.touchsreentab.database.entity.Singer;
import com.vk2.touchsreentab.database.entity.Song;
import com.vk2.touchsreentab.databinding.ItemRecyclerviewBinding;
import com.vk2.touchsreentab.databinding.ItemSongsBinding;
import com.vk2.touchsreentab.model.viewmodel.SingerVewModel;



public class MultiViewAdapter extends PagedListAdapter<Song, RecyclerView.ViewHolder> {
    private Context context;

    public MultiViewAdapter() {
        super(Song.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        if (i == 0) {
            ItemRecyclerviewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_recyclerview, viewGroup, false);
            return new RecyclerViewHolder(binding);
        } else {
            ItemSongsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_songs, viewGroup, false);
            return new SongViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final Song song = getItem(i);
        if (song == null) return;
        if (i == 0) {
            final ArtistAdapter artistAdapter = new ArtistAdapter();
            ((RecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem.setLayoutManager(new LinearLayoutManager(context, LinearLayout.HORIZONTAL, false));
            ((RecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem.setAdapter(artistAdapter);
            SingerVewModel singerVewModel = ViewModelProviders.of((FragmentActivity) context).get(SingerVewModel.class);
            singerVewModel.getSingerData(MyApplication.appDatabase.singerDao());
            singerVewModel.listSinger.observe((LifecycleOwner) context, new Observer<PagedList<Singer>>() {
                @Override
                public void onChanged(@Nullable PagedList<Singer> artists) {
                    artistAdapter.submitList(artists);
                }
            });

        } else {
            ((SongViewHolder) viewHolder).songBinding.setSong(song);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
