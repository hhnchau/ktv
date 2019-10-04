package com.vk2.touchsreentab.adapter;

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
import com.vk2.touchsreentab.adapter.viewholder.CategoryRecyclerViewHolder;
import com.vk2.touchsreentab.adapter.viewholder.SingerRecyclerViewHolder;
import com.vk2.touchsreentab.adapter.viewholder.SongViewHolder;
import com.vk2.touchsreentab.database.entity.Singer;
import com.vk2.touchsreentab.database.entity.Song;
import com.vk2.touchsreentab.databinding.ItemCategoryRecyclerviewBinding;
import com.vk2.touchsreentab.databinding.ItemSingerRecyclerviewBinding;
import com.vk2.touchsreentab.databinding.ItemSongsBinding;
import com.vk2.touchsreentab.model.viewmodel.SingerVewModel;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;


public class MultiViewRecommendAdapter extends PagedListAdapter<Song, RecyclerView.ViewHolder> {
    private Context context;
    private String search = "";

    public MultiViewRecommendAdapter() {
        super(Song.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        if (i == 0) {
            ItemCategoryRecyclerviewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_category_recyclerview, viewGroup, false);
            return new CategoryRecyclerViewHolder(binding);
        } else if (i == 1) {
            ItemSingerRecyclerviewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_singer_recyclerview, viewGroup, false);
            return new SingerRecyclerViewHolder(binding);
        } else {
            ItemSongsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_songs, viewGroup, false);
            return new SongViewHolder(binding);
        }
    }
    private List<String> getListCategory() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String categoryObject = new String();
            list.add(categoryObject);
        }
        return list;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final Song song = getItem(i);
        if (song == null) return;
        if (i == 0) {
            final CategoryAdapter categoryAdapter = new CategoryAdapter(context,getListCategory());
            ((CategoryRecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem.setAdapter(categoryAdapter);
            ((CategoryRecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem.scrollToPosition(2);
            ((CategoryRecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem.setItemTransformer(new ScaleTransformer.Builder()
                    .setMaxScale(1.2f)
                    .setPivotX(Pivot.X.CENTER)
                    .build());
            ((CategoryRecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem.setSlideOnFlingThreshold(3000);
        } else if(i ==1) {
            final ArtistAdapter artistAdapter = new ArtistAdapter();
            ((SingerRecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem.setLayoutManager(new LinearLayoutManager(context, LinearLayout.HORIZONTAL, false));
            ((SingerRecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem.setAdapter(artistAdapter);
            final SingerVewModel singerVewModel = ViewModelProviders.of((FragmentActivity) context).get(SingerVewModel.class);
            singerVewModel.getAllListSinger();
            singerVewModel.listSingerOnline.observe((FragmentActivity) context, new Observer<PagedList<Singer>>() {
                @Override
                public void onChanged(@Nullable PagedList<Singer> artists) {

                    artistAdapter.submitList(artists);
                }
            });
        }else{
            ((SongViewHolder) viewHolder).songBinding.setSong(song);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}