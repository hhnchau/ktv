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
import com.vk2.touchsreentab.adapter.viewholder.SettingNetworkViewHolder;
import com.vk2.touchsreentab.databinding.FragmentSettingSystemBinding;
import com.vk2.touchsreentab.databinding.ItemSettingNetworkBinding;
import com.vk2.touchsreentab.model.setting.General;

import java.util.ArrayList;
import java.util.List;

public class FragmentSystem extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSettingSystemBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting_system, container, false);

        final List<General> systems = new ArrayList<>();
        systems.add(new General(getString(R.string.txt_online_update), "Version:1.0.0"));
        systems.add(new General(getString(R.string.txt_usb_update), ""));
        systems.add(new General(getString(R.string.txt_backup), "Backup 2019/08012"));
        systems.add(new General(getString(R.string.txt_restore_system), ""));
        systems.add(new General(getString(R.string.txt_sharing_settings), ""));
        systems.add(new General(getString(R.string.txt_disk_capacity), "2.4T/3T"));
        systems.add(new General(getString(R.string.txt_about), "Name:VietKTV K2 New"));
        systems.add(new General(getString(R.string.txt_help), "Support and Help"));


        SettingAdapter<SettingNetworkViewHolder> adapter = new SettingAdapter<SettingNetworkViewHolder>() {
            @NonNull
            @Override
            public SettingNetworkViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                ItemSettingNetworkBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_setting_network, viewGroup, false);
                return new SettingNetworkViewHolder(binding);
            }

            @Override
            public void onBindViewHolder(@NonNull final SettingNetworkViewHolder holder, int i) {
                final General system = systems.get(holder.getAdapterPosition());
                if (system != null) {
                    holder.networkBinding.setGeneral(system);
                    holder.networkBinding.root.setOnClickListener(new View.OnClickListener() {
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
                                    break;
                                case 5:
                                    break;
                                case 6:
                                    if (getActivity() != null)
                                        ((SettingActivity) getActivity()).showFragmentAbout();
                                    break;
                                case 7:
                                    break;
                            }
                        }
                    });
                }
            }

            @Override
            public int getItemCount() {
                return systems.size();
            }
        };

        binding.rcv.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        binding.rcv.setLayoutManager(layoutManager);

        return binding.getRoot();
    }
}
