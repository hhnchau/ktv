package com.vk2.touchsreentab.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.adapter.viewholder.SingerRecyclerViewHolder;
import com.vk2.touchsreentab.adapter.viewholder.SongViewHolder;
import com.vk2.touchsreentab.database.entity.Singer;
import com.vk2.touchsreentab.database.entity.Song;
import com.vk2.touchsreentab.databinding.ItemSingerRecyclerviewBinding;
import com.vk2.touchsreentab.databinding.ItemSongsBinding;
import com.vk2.touchsreentab.model.Ablum;
import com.vk2.touchsreentab.model.viewmodel.SingerVewModel;
import com.vk2.touchsreentab.utils.OnSingleClickListener;


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
            ((SingerRecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            ((SingerRecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem.setAdapter(artistAdapter);
            final SingerVewModel singerVewModel = ViewModelProviders.of((FragmentActivity) context).get(SingerVewModel.class);
            singerVewModel.getAllListSinger(context);
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
