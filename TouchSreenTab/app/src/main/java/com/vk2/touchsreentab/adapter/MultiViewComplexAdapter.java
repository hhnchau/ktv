package com.vk2.touchsreentab.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.adapter.viewholder.RecyclerViewHolder;
import com.vk2.touchsreentab.adapter.viewholder.SongViewHolder;

import com.vk2.touchsreentab.application.MyApplication;
import com.vk2.touchsreentab.database.entity.Singer;
import com.vk2.touchsreentab.database.entity.Song;
import com.vk2.touchsreentab.databinding.ItemRecyclerviewBinding;
import com.vk2.touchsreentab.databinding.ItemSongsBinding;
import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentez;
import com.vk2.touchsreentab.model.TextSearch;
import com.vk2.touchsreentab.model.viewmodel.TextSearchViewModel;
import com.vk2.touchsreentab.model.viewmodel.SingerVewModel;
import com.vk2.touchsreentab.utils.OnSingleClickListener;


public class MultiViewComplexAdapter extends PagedListAdapter<Song, RecyclerView.ViewHolder> {
    private Context context;
    private String search = "";

    public MultiViewComplexAdapter() {
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
            ((RecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            ((RecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem.setAdapter(artistAdapter);
            final SingerVewModel singerVewModel = ViewModelProviders.of((FragmentActivity) context).get(SingerVewModel.class);
            singerVewModel.getSearchSinger(MyApplication.appDatabase.singerDao());
            singerVewModel.listSearchSinger.observe((LifecycleOwner) context, new Observer<PagedList<Singer>>() {
                @Override
                public void onChanged(@Nullable PagedList<Singer> singers) {
                    if (!TextUtils.isEmpty(search))
                        artistAdapter.submitList(singers);
                }
            });

            TextSearchViewModel textSearchViewModel = ViewModelProviders.of((FragmentActivity) context).get(TextSearchViewModel.class);
            textSearchViewModel.getTextSearch().observe((LifecycleOwner) context, new Observer<TextSearch>() {
                @Override
                public void onChanged(TextSearch textSearch) {
                    if (textSearch.getFrg() == Fragmentez.SEARCH_COMPLEX_FRAGMENT) {
                        search = textSearch.getTextSearch();
                        Log.d("TAG - TextSearch: ", search);
                        singerVewModel.search.setValue(search);
                    }
                }
            });

            artistAdapter.setOnItemClick(new ArtistAdapter.OnItemClick() {
                @Override
                public void onClick(Singer singer) {
                    if (onItemClick != null) onItemClick.onImageSinger(singer);
                }
            });

        } else {
            ((SongViewHolder) viewHolder).songBinding.setSong(song);
            ((SongViewHolder) viewHolder).songBinding.setTextSearch(search);

            ((SongViewHolder) viewHolder).songBinding.imgSong.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    if (onItemClick != null) onItemClick.onImageClick(song);
                    enable();
                }
            });

            ((SongViewHolder) viewHolder).songBinding.song.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    if (onItemClick != null) onItemClick.onItemClick(song);
                    enable();
                }
            });

            ((SongViewHolder) viewHolder).songBinding.imgMore.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    if (onItemClick != null) onItemClick.onIconClick(song);
                    enable();
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public interface OnItemClick {
        void onImageSinger(Singer singer);

        void onImageClick(Song song);

        void onItemClick(Song song);

        void onIconClick(Song song);
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

}
