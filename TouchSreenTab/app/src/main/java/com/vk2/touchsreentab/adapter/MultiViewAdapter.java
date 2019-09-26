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

import android.text.TextUtils;
import android.util.Log;
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
import com.vk2.touchsreentab.model.viewmodel.SearchInputViewModel;
import com.vk2.touchsreentab.model.viewmodel.SingerVewModel;


public class MultiViewAdapter extends PagedListAdapter<Song, RecyclerView.ViewHolder> {
    private Context context;
    private String textSearch = "";

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
            final SingerVewModel singerVewModel = ViewModelProviders.of((FragmentActivity) context).get(SingerVewModel.class);
            singerVewModel.getSearchSinger(MyApplication.appDatabase.singerDao());
            singerVewModel.listSearchSinger.observe((LifecycleOwner) context, new Observer<PagedList<Singer>>() {
                @Override
                public void onChanged(@Nullable PagedList<Singer> singers) {
                    if (!TextUtils.isEmpty(textSearch))
                        artistAdapter.submitList(singers);
                }
            });

            SearchInputViewModel searchInputViewModel = ViewModelProviders.of((FragmentActivity) context).get(SearchInputViewModel.class);
            searchInputViewModel.getSearchInput().observe((LifecycleOwner) context, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    textSearch = s;
                    singerVewModel.search.setValue(s);
                }
            });

        } else {
            ((SongViewHolder) viewHolder).songBinding.setSong(song);
            ((SongViewHolder) viewHolder).songBinding.setTextSearch(textSearch);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
