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
import com.vk2.touchsreentab.databinding.FragmentSettingSongManagementBinding;
import com.vk2.touchsreentab.databinding.ItemSettingSongManagementBinding;
import com.vk2.touchsreentab.databinding.LayoutRecyclerviewBinding;
import com.vk2.touchsreentab.model.setting.SongManagement;

import java.util.ArrayList;
import java.util.List;

public class FragmentSongManagement extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSettingSongManagementBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting_song_management, container, false);
        binding.sticky.setSticky(getString(R.string.txt_song_management));
        binding.sticky.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null)
                    getActivity().onBackPressed();
            }
        });

        final List<SongManagement> songManagements = new ArrayList<>();
        songManagements.add(new SongManagement(R.mipmap.ic_delete_song, getString(R.string.txt_user_delete_song)));
        songManagements.add(new SongManagement(R.mipmap.ic_import_song, getString(R.string.txt_import_song)));
        songManagements.add(new SongManagement(R.mipmap.ic_export_song, getString(R.string.txt_export_song)));
        songManagements.add(new SongManagement(R.mipmap.ic_backup_song, getString(R.string.txt_backup_song)));
        songManagements.add(new SongManagement(R.mipmap.ic_restore_song, getString(R.string.txt_restore_song)));
        songManagements.add(new SongManagement(R.mipmap.ic_import_singer, getString(R.string.txt_import_singer)));
        songManagements.add(new SongManagement(R.mipmap.ic_import_lyrics, getString(R.string.txt_import_lyrics)));


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


        binding.recyclerview.rcv.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        binding.recyclerview.rcv.setLayoutManager(layoutManager);

        return binding.getRoot();
    }
}
