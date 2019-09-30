package com.vk2.touchsreentab.adapter;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.adapter.viewholder.SingerDescriptionViewHolder;
import com.vk2.touchsreentab.adapter.viewholder.SongViewHolder;
import com.vk2.touchsreentab.database.entity.Song;
import com.vk2.touchsreentab.databinding.ItemDescriptionBinding;
import com.vk2.touchsreentab.databinding.ItemSongsBinding;


public class MultiViewSingerAdapter extends PagedListAdapter<Song, RecyclerView.ViewHolder> {
    private Context context;
    private String textSearch = "";

    public MultiViewSingerAdapter() {
        super(Song.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        if (i == 0) {
            ItemDescriptionBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_description, viewGroup, false);
            return new SingerDescriptionViewHolder(binding);
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
//            final ArtistAdapter artistAdapter = new ArtistAdapter();
//            ((RecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem.setLayoutManager(new LinearLayoutManager(context, LinearLayout.HORIZONTAL, false));
//            ((RecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem.setAdapter(artistAdapter);
//            final SingerVewModel singerVewModel = ViewModelProviders.of((FragmentActivity) context).get(SingerVewModel.class);
//            singerVewModel.getSearchSinger(MyApplication.appDatabase.singerDao());
//            singerVewModel.listSearchSinger.observe((LifecycleOwner) context, new Observer<PagedList<Singer>>() {
//                @Override
//                public void onChanged(@Nullable PagedList<Singer> singers) {
//                    if (!TextUtils.isEmpty(textSearch))
//                        artistAdapter.submitList(singers);
//                }
//            });
//
//            TextSearchViewModel searchInputViewModel = ViewModelProviders.of((FragmentActivity) context).get(TextSearchViewModel.class);
//            searchInputViewModel.getSearchInput().observe((LifecycleOwner) context, new Observer<String>() {
//                @Override
//                public void onChanged(@Nullable String s) {
//                    textSearch = s;
//                    Log.d("TAG - TextSearch: " , textSearch);
//                    singerVewModel.search.setValue(s);
//                }
//            });

        } else {
            ((SongViewHolder) viewHolder).songBinding.setSong(song);
            ((SongViewHolder) viewHolder).songBinding.setNoImage(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
