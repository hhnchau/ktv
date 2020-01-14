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
import com.vk2.touchsreentab.model.setting.General;


import java.util.ArrayList;
import java.util.List;

public class FragmentNetwork extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutRecyclerviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_recyclerview, container, false);

        final List<General> generals = new ArrayList<>();
        generals.add(new General(getString(R.string.txt_network), "Not Connected"));
        generals.add(new General(getString(R.string.txt_bluetooth), "OFF"));
        generals.add(new General(getString(R.string.txt_wlan), "Devitop"));
        generals.add(new General(getString(R.string.txt_hotspot), "OFF"));

        SettingAdapter<SettingNetworkViewHolder> adapter = new SettingAdapter<SettingNetworkViewHolder>() {
            @NonNull
            @Override
            public SettingNetworkViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                ItemSettingNetworkBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_setting_network, viewGroup, false);
                return new SettingNetworkViewHolder(binding);
            }

            @Override
            public void onBindViewHolder(@NonNull SettingNetworkViewHolder holder, int i) {
                final General general = generals.get(holder.getAdapterPosition());
                if (general != null) {
                    holder.networkBinding.setGeneral(general);
                    holder.networkBinding.root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                }
            }

            @Override
            public int getItemCount() {
                return generals.size();
            }
        };


        binding.rcv.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        binding.rcv.setLayoutManager(layoutManager);

        return binding.getRoot();
    }
}
