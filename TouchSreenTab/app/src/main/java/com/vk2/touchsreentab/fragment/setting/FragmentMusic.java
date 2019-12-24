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
import com.vk2.touchsreentab.activity.SettingActivity;
import com.vk2.touchsreentab.adapter.SettingAdapter;
import com.vk2.touchsreentab.adapter.viewholder.SettingMusicViewHolder;
import com.vk2.touchsreentab.databinding.ItemSettingMusicBinding;
import com.vk2.touchsreentab.databinding.LayoutRecyclerviewBinding;
import com.vk2.touchsreentab.model.setting.Music;

import java.util.ArrayList;
import java.util.List;

public class FragmentMusic extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutRecyclerviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_recyclerview, container, false);


        final List<Music> musics = new ArrayList<>();
        musics.add(new Music("Song Management", R.mipmap.ic_song_update));
        musics.add(new Music("Cloud Update", R.mipmap.ic_cloud_update));
        musics.add(new Music("Disk Update", R.mipmap.ic_hdd_update));

        SettingAdapter<SettingMusicViewHolder> adapter = new SettingAdapter<SettingMusicViewHolder>() {

            @NonNull
            @Override
            public SettingMusicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                ItemSettingMusicBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_setting_music, viewGroup, false);
                return new SettingMusicViewHolder(binding);
            }

            @Override
            public void onBindViewHolder(@NonNull final SettingMusicViewHolder holder, final int i) {
                Music music = musics.get(holder.getAdapterPosition());
                if (music != null) {
                    holder.musicBinding.setMusic(music);
                    holder.musicBinding.root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (getActivity() != null && holder.getAdapterPosition() == 0) {
                                ((SettingActivity) getActivity()).showFragmentSongManagement();
                            }
                        }
                    });
                }
            }

            @Override
            public int getItemCount() {
                return musics.size();
            }
        };


        binding.rcv.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        binding.rcv.setLayoutManager(layoutManager);

        return binding.getRoot();
    }
}
