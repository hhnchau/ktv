package com.vk2.touchsreentab.fragment.setting;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.adapter.SettingAdapter;
import com.vk2.touchsreentab.adapter.viewholder.SettingBroadcastSongsViewHolder;
import com.vk2.touchsreentab.databinding.FragmentSettingBroadcastSongsBinding;
import com.vk2.touchsreentab.databinding.ItemSettingBroadcastSongsBinding;
import com.vk2.touchsreentab.model.setting.SongBroadcast;

import java.util.ArrayList;
import java.util.List;

public class FragmentBroadcastSongs extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentSettingBroadcastSongsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting_broadcast_songs, container, false);
        binding.sticky.setSticky(getString(R.string.txt_list_of_broadcast_songs));
        binding.sticky.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null)
                    getActivity().onBackPressed();
            }
        });

        final List<SongBroadcast> songs = new ArrayList<>();
        songs.add(new SongBroadcast(1, "Song 1", "Singer1"));
        songs.add(new SongBroadcast(2, "Song 2", "Singer2"));
        songs.add(new SongBroadcast(3, "Song 3", "Singer3"));
        songs.add(new SongBroadcast(4, "Song 4", "Singer4"));
        songs.add(new SongBroadcast(5, "Song 5", "Singer5"));
        songs.add(new SongBroadcast(6, "Song 6", "Singer6"));
        songs.add(new SongBroadcast(7, "Song 7", "Singer7"));
        songs.add(new SongBroadcast(8, "Song 8", "Singer8"));
        songs.add(new SongBroadcast(9, "Song 9", "Singer9"));
        songs.add(new SongBroadcast(10, "Song 10", "Singer10"));


        SettingAdapter<SettingBroadcastSongsViewHolder> adapter = new SettingAdapter<SettingBroadcastSongsViewHolder>() {
            @NonNull
            @Override
            public SettingBroadcastSongsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                ItemSettingBroadcastSongsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_setting_broadcast_songs, viewGroup, false);
                return new SettingBroadcastSongsViewHolder(binding);
            }

            @Override
            public void onBindViewHolder(@NonNull SettingBroadcastSongsViewHolder holder, int i) {
                SongBroadcast song = songs.get(holder.getAdapterPosition());
                if (song != null){
                    holder.binding.setBroadcast(song);
                }
            }

            @Override
            public int getItemCount() {
                return songs.size();
            }
        };

        binding.recyclerview.rcv.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerview.rcv.setLayoutManager(layoutManager);
        binding.recyclerview.rcv.addItemDecoration(getDivider(getActivity(), layoutManager));

        return binding.getRoot();
    }

    private DividerItemDecoration getDivider(Context context, LinearLayoutManager layoutManager) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(context.getResources().getDrawable(R.drawable.divider));
        return dividerItemDecoration;
    }
}
