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
import com.vk2.touchsreentab.adapter.viewholder.SettingBroadcastManagementViewHolder;
import com.vk2.touchsreentab.databinding.FragmentSettingBroadcastManagementBinding;
import com.vk2.touchsreentab.databinding.ItemSettingBroadcastManagementBinding;
import com.vk2.touchsreentab.model.setting.General;

import java.util.ArrayList;
import java.util.List;

public class FragmentBroadcastManagement extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSettingBroadcastManagementBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting_broadcast_management, container, false);
        binding.sticky.setSticky(getString(R.string.txt_broadcast_management));
        binding.sticky.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null)
                    getActivity().onBackPressed();
            }
        });

        final List<General> managements = new ArrayList<>();
        managements.add(new General(getString(R.string.txt_specify_public_song), ""));
        managements.add(new General(getString(R.string.txt_broadcast_song_track), ""));
        managements.add(new General(getString(R.string.txt_track_switching_during_broadcast), ""));
        managements.add(new General(getString(R.string.txt_list_of_broadcast_songs), ""));
        managements.add(new General(getString(R.string.txt_add_broadcast_songs), ""));
        managements.add(new General(getString(R.string.txt_import_songs_with_usb), ""));

        SettingAdapter<SettingBroadcastManagementViewHolder> adapter = new SettingAdapter<SettingBroadcastManagementViewHolder>() {
            @NonNull
            @Override
            public SettingBroadcastManagementViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                ItemSettingBroadcastManagementBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_setting_broadcast_management, viewGroup, false);
                return new SettingBroadcastManagementViewHolder(binding);
            }

            @Override
            public void onBindViewHolder(@NonNull final SettingBroadcastManagementViewHolder holder, int i) {
                General management = managements.get(holder.getAdapterPosition());
                if (management != null) {
                    holder.binding.setGeneral(management);
                    holder.binding.root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (holder.getAdapterPosition()) {
                                case 0:
                                    break;
                                case 1:
                                    break;
                                case 2:
                                    break;
                                case 3:
                                    break;
                                case 4:
                                    if (getActivity() != null)
                                        ((SettingActivity) getActivity()).showFragmentBroadcastSongs();
                                    break;
                                case 5:
                                    break;
                            }
                        }
                    });
                }
            }

            @Override
            public int getItemCount() {
                return managements.size();
            }
        };

        binding.recyclerview.rcv.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        binding.recyclerview.rcv.setLayoutManager(layoutManager);

        return binding.getRoot();
    }
}
