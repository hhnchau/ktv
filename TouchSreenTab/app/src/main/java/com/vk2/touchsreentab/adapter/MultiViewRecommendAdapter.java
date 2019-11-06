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
import android.view.View;
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
import com.vk2.touchsreentab.model.Ablum;
import com.vk2.touchsreentab.model.viewmodel.AblumViewModel;
import com.vk2.touchsreentab.model.viewmodel.SingerVewModel;
import com.vk2.touchsreentab.utils.OnSingleClickListener;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;


public class MultiViewRecommendAdapter extends PagedListAdapter<Song, RecyclerView.ViewHolder> {
    private Context context;

    public MultiViewRecommendAdapter() {
        super(Song.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
//        if (i == 0) {
//            ItemCategoryRecyclerviewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_category_recyclerview, viewGroup, false);
//            return new CategoryRecyclerViewHolder(binding);
//        } else
            if (i == 1) {
            ItemSingerRecyclerviewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_singer_recyclerview, viewGroup, false);
            return new SingerRecyclerViewHolder(binding);
        } else {
            ItemSongsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_songs, viewGroup, false);
            return new SongViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        final Song song = getItem(i);
        if (song == null) return;
//        tạm đóng để đổi sang dùng CustomDiscreteScrollView hiển thị album
//
//        if (i == 0) {
//
//            final AblumAdapter ablumAdapter = new AblumAdapter();
//            final InfiniteScrollAdapter InfiniteScrollAdapter = com.yarolegovich.discretescrollview.InfiniteScrollAdapter.wrap(ablumAdapter);
//            ((CategoryRecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem.setAdapter(InfiniteScrollAdapter);
//            ((CategoryRecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem.setItemTransformer(new ScaleTransformer.Builder()
//                    .setMaxScale(1.3f)
//                    .setPivotX(Pivot.X.CENTER)
//                    .build());
//            ((CategoryRecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem.setSlideOnFlingThreshold(5000);
//            ((CategoryRecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem.setOrientation(DSVOrientation.HORIZONTAL);
//            ((CategoryRecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem.setOffscreenItems(2);
//            ((CategoryRecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem.setItemTransitionTimeMillis(150);
//            ((CategoryRecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem.setOverScrollEnabled(false);
//            AblumViewModel ablumViewModel = ViewModelProviders.of((FragmentActivity) context).get(AblumViewModel.class);
//            ablumViewModel.getAllListAblum();
//            ablumViewModel.listAblumOnline.observe((FragmentActivity) context, new Observer<PagedList<Ablum>>() {
//                @Override
//                public void onChanged(@Nullable PagedList<Ablum> ablums) {
//                    ablumAdapter.submitList(ablums);
//                }
//            });
//
//            ablumAdapter.setOnItemClick(new AblumAdapter.OnItemClick() {
//                @Override
//                public void onItemClick(Ablum ablum) {
//                    if (onItemClick != null) onItemClick.onBannerClick(ablum);
//                }
//            });
//        } else
            if (i == 1) {
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

            artistAdapter.setOnItemClick(new ArtistAdapter.OnItemClick() {
                @Override
                public void onClick(Singer singer) {
                    if (onItemClick != null) onItemClick.onImageSinger(singer);
                }
            });

        } else {
            ((SongViewHolder) viewHolder).songBinding.setSong(song);

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
        void onBannerClick(Ablum album);

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
