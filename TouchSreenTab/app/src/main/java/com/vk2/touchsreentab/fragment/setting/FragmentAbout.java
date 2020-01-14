package com.vk2.touchsreentab.fragment.setting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.adapter.SettingAdapter;
import com.vk2.touchsreentab.adapter.viewholder.SettingAboutViewHolder;
import com.vk2.touchsreentab.databinding.FragmentSettingAboutBinding;
import com.vk2.touchsreentab.databinding.ItemSettingAboutBinding;
import com.vk2.touchsreentab.model.setting.General;

import java.util.ArrayList;
import java.util.List;

public class FragmentAbout extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentSettingAboutBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting_about, container, false);
        binding.sticky.setSticky(getString(R.string.txt_about));
        binding.sticky.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null)
                    getActivity().onBackPressed();
            }
        });

        final List<General> abouts = new ArrayList<>();
        abouts.add(new General(getString(R.string.txt_name), "Viet KTV K2 New"));
        abouts.add(new General(getString(R.string.txt_software_version), "1.0.0"));
        abouts.add(new General(getString(R.string.txt_device_id), "10243243209"));
        abouts.add(new General(getString(R.string.txt_mac_code), "0C-9D-92-C1-FB-86"));

        SettingAdapter<SettingAboutViewHolder> adapter = new SettingAdapter<SettingAboutViewHolder>() {

            @NonNull
            @Override
            public SettingAboutViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                ItemSettingAboutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_setting_about, viewGroup, false);
                return new SettingAboutViewHolder(binding);
            }

            @Override
            public void onBindViewHolder(@NonNull SettingAboutViewHolder holder, int i) {
                General about = abouts.get(holder.getAdapterPosition());
                if (about != null) {
                    holder.binding.setAbout(about);
                }
            }

            @Override
            public int getItemCount() {
                return abouts.size();
            }
        };

        binding.recyclerview.rcv.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerview.rcv.setLayoutManager(layoutManager);

        return binding.getRoot();
    }
}
