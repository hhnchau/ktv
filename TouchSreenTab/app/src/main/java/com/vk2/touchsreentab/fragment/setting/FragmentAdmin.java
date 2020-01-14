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
import android.widget.CompoundButton;
import android.widget.Toast;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.activity.SettingActivity;
import com.vk2.touchsreentab.adapter.SettingAdapter;
import com.vk2.touchsreentab.adapter.viewholder.SettingGeneralViewHolder;
import com.vk2.touchsreentab.databinding.FragmentSettingAdminBinding;
import com.vk2.touchsreentab.databinding.ItemSettingGeneralBinding;
import com.vk2.touchsreentab.model.setting.General;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdmin extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentSettingAdminBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting_admin, container, false);

        final List<General> admins = new ArrayList<>();
        admins.add(new General(getString(R.string.txt_password), General.TOGGLE, true));
        admins.add(new General(getString(R.string.txt_change_password), General.VALUES, ""));

        final SettingAdapter<SettingGeneralViewHolder> adapter = new SettingAdapter<SettingGeneralViewHolder>() {

            @NonNull
            @Override
            public SettingGeneralViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                ItemSettingGeneralBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_setting_general, viewGroup, false);
                return new SettingGeneralViewHolder(binding);
            }

            @Override
            public void onBindViewHolder(@NonNull final SettingGeneralViewHolder holder, int i) {
                General admin = admins.get(holder.getAdapterPosition());
                if (admin != null) {
                    holder.generalBinding.setGeneral(admin);
                    holder.generalBinding.root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (holder.getAdapterPosition() == 1){
                                if (getActivity() != null)
                                    ((SettingActivity) getActivity()).showFragmentChangePassword();
                            }
                        }
                    });

                    holder.generalBinding.toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            new DialogPassword().build(getActivity());
                        }
                    });

                }
            }

            @Override
            public int getItemCount() {
                return admins.size();
            }
        };
        binding.rcv.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        binding.rcv.setLayoutManager(layoutManager);
        return binding.getRoot();
    }
}
