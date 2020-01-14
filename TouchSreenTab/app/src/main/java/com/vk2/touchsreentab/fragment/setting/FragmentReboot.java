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
import com.vk2.touchsreentab.databinding.FragmentSettingRebootBinding;
import com.vk2.touchsreentab.databinding.ItemSettingSongManagementBinding;
import com.vk2.touchsreentab.model.setting.SongManagement;

import java.util.ArrayList;
import java.util.List;

public class FragmentReboot extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentSettingRebootBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting_reboot, container, false);

        final List<SongManagement> powers = new ArrayList<>();
        powers.add(new SongManagement(R.mipmap.ic_shutdown, getString(R.string.txt_shutdown)));
        powers.add(new SongManagement(R.mipmap.ic_reboot, getString(R.string.txt_reboot)));
        SettingAdapter<SettingSongManagementViewHolder> adapter = new SettingAdapter<SettingSongManagementViewHolder>() {

            @NonNull
            @Override
            public SettingSongManagementViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                ItemSettingSongManagementBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_setting_song_management, viewGroup, false);
                return new SettingSongManagementViewHolder(binding);
            }

            @Override
            public void onBindViewHolder(@NonNull SettingSongManagementViewHolder holder, int i) {
                SongManagement power = powers.get(holder.getAdapterPosition());
                if (power != null) {
                    holder.songBinding.setSong(power);
                    holder.songBinding.root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                }
            }

            @Override
            public int getItemCount() {
                return powers.size();
            }
        };
        binding.rcv.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        binding.rcv.setLayoutManager(layoutManager);
        return binding.getRoot();
    }
}
