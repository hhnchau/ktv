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
import com.vk2.touchsreentab.adapter.viewholder.SettingNetworkViewHolder;
import com.vk2.touchsreentab.databinding.ItemSettingNetworkBinding;
import com.vk2.touchsreentab.databinding.LayoutRecyclerviewBinding;
import com.vk2.touchsreentab.model.setting.Network;

import java.util.ArrayList;
import java.util.List;

public class FragmentGeneral extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutRecyclerviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_recyclerview, container, false);

        final List<Network> networks = new ArrayList<>();
        networks.add(new Network("Network", ""));
        networks.add(new Network("Bluetooth", ""));
        networks.add(new Network("Wlan", ""));
        networks.add(new Network("HotSpot", ""));
        networks.add(new Network("Network", ""));
        networks.add(new Network("Bluetooth", ""));
        networks.add(new Network("Wlan", ""));
        networks.add(new Network("HotSpot", ""));

        SettingAdapter<SettingNetworkViewHolder> adapter = new SettingAdapter<SettingNetworkViewHolder>() {

            @NonNull
            @Override
            public SettingNetworkViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                ItemSettingNetworkBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_setting_network, viewGroup, false);
                return new SettingNetworkViewHolder(binding);
            }

            @Override
            public void onBindViewHolder(@NonNull SettingNetworkViewHolder holder, final int i) {
                Network network = networks.get(holder.getAdapterPosition());
                if (network != null) {
                    holder.networkBinding.setNetwork(network);
                    holder.networkBinding.root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                }
            }

            @Override
            public int getItemCount() {
                return networks.size();
            }
        };


        binding.rcv.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        binding.rcv.setLayoutManager(layoutManager);

        return binding.getRoot();
    }
}
