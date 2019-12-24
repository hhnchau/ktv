package com.vk2.touchsreentab.fragment.setting;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.adapter.SettingAdapter;
import com.vk2.touchsreentab.adapter.viewholder.SettingSongManagementViewHolder;
import com.vk2.touchsreentab.databinding.ItemSettingSongManagementBinding;
import com.vk2.touchsreentab.databinding.LayoutRecyclerviewBinding;
import com.vk2.touchsreentab.model.setting.SongManagement;

import java.util.ArrayList;
import java.util.List;

public class FragmentSongManagement extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutRecyclerviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_recyclerview, container, false);

        final List<SongManagement> songManagements = new ArrayList<>();
        songManagements.add(new SongManagement(R.mipmap.ic_hdd_update,"Network"));



        SettingAdapter<SettingSongManagementViewHolder> adapter = new SettingAdapter<SettingSongManagementViewHolder>() {

            @NonNull
            @Override
            public SettingSongManagementViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                ItemSettingSongManagementBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_setting_song_management, viewGroup, false);
                return new SettingSongManagementViewHolder(binding);
            }

            @Override
            public void onBindViewHolder(@NonNull SettingSongManagementViewHolder holder, final int i) {
                SongManagement songManagement = songManagements.get(holder.getAdapterPosition());
                if (songManagement != null) {
                    holder.songBinding.setSong(songManagement);
                    holder.songBinding.root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                }
            }

            @Override
            public int getItemCount() {
                return songManagements.size();
            }
        };


        binding.rcv.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        binding.rcv.setLayoutManager(layoutManager);

        return binding.getRoot();
    }
}
