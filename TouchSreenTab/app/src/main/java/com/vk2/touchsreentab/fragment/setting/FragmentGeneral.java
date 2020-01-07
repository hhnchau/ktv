package com.vk2.touchsreentab.fragment.setting;

import android.app.Dialog;
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
import com.vk2.touchsreentab.adapter.viewholder.SettingNetworkViewHolder;
import com.vk2.touchsreentab.databinding.ItemSettingGeneralBinding;
import com.vk2.touchsreentab.databinding.ItemSettingNetworkBinding;
import com.vk2.touchsreentab.databinding.LayoutRecyclerviewBinding;
import com.vk2.touchsreentab.model.setting.Checkbox;
import com.vk2.touchsreentab.model.setting.General;
import com.vk2.touchsreentab.model.setting.Network;

import java.util.ArrayList;
import java.util.List;

public class FragmentGeneral extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final LayoutRecyclerviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_recyclerview, container, false);

        final List<General> networks = new ArrayList<>();
        networks.add(new General(getString(R.string.txt_resolution_setting), General.VALUES, ""));
        networks.add(new General(getString(R.string.txt_on_screen_time), General.VALUES, ""));
        networks.add(new General(getString(R.string.txt_rating_switch), General.TOGGLE, true));
        networks.add(new General(getString(R.string.txt_cloud_song_library_switch), General.TOGGLE, true));
        networks.add(new General(getString(R.string.txt_broadcast_management), General.VALUES, ""));
        networks.add(new General(getString(R.string.txt_ranking_mode), General.VALUES, ""));
        networks.add(new General(getString(R.string.txt_subtitle_settings), General.VALUES, ""));
        networks.add(new General(getString(R.string.txt_midi_settings), General.VALUES, ""));
        networks.add(new General(getString(R.string.txt_theme), General.VALUES, ""));


        SettingAdapter<SettingGeneralViewHolder> adapter = new SettingAdapter<SettingGeneralViewHolder>() {

            @NonNull
            @Override
            public SettingGeneralViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                ItemSettingGeneralBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_setting_general, viewGroup, false);
                return new SettingGeneralViewHolder(binding);
            }

            @Override
            public void onBindViewHolder(@NonNull final SettingGeneralViewHolder holder, final int i) {
                General general = networks.get(holder.getAdapterPosition());
                if (general != null) {
                    holder.generalBinding.setGeneral(general);
                    holder.generalBinding.root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (holder.getAdapterPosition()) {
                                case 0:
                                    new DialogCheckbox() {
                                        @Override
                                        public void onListener(String value) {
                                            Toast.makeText(getContext(), "True", Toast.LENGTH_SHORT).show();
                                        }
                                    }.build(getActivity(), getResolutionSettings());
                                    break;
                                case 1:
                                    new DialogCheckbox() {
                                        @Override
                                        public void onListener(String value) {

                                        }
                                    }.build(getActivity(), getOnScreenTime());
                                    break;
                                case 4:
                                    break;
                                case 5:
                                    new DialogCheckbox() {
                                        @Override
                                        public void onListener(String value) {

                                        }
                                    }.build(getActivity(), getRankingMode());
                                    break;
                                case 6:
                                    new DialogPassword().build(getActivity());
                                    break;
                                case 7:
                                    ((SettingActivity)getActivity()).showFragmentChangePassword();
                                    break;
                            }

                        }
                    });

                    holder.generalBinding.toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (b && holder.getAdapterPosition() == 2) {
                                Toast.makeText(getContext(), "True", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "False", Toast.LENGTH_SHORT).show();
                            }
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

    private List<Checkbox> getResolutionSettings() {
        List<Checkbox> checkboxes = new ArrayList<>();
        checkboxes.add(new Checkbox(false, "480 (HDMI/AV)"));
        checkboxes.add(new Checkbox(true, "720P (HDMI)"));
        checkboxes.add(new Checkbox(false, "1080P (HDMI)"));
        return checkboxes;
    }

    private List<Checkbox> getOnScreenTime() {
        List<Checkbox> checkboxes = new ArrayList<>();
        checkboxes.add(new Checkbox(false, "60 S"));
        checkboxes.add(new Checkbox(true, "90 S"));
        checkboxes.add(new Checkbox(false, "3 Min"));
        checkboxes.add(new Checkbox(false, "5 Min"));
        checkboxes.add(new Checkbox(false, "10 Min"));
        return checkboxes;
    }

    private List<Checkbox> getRankingMode() {
        List<Checkbox> checkboxes = new ArrayList<>();
        checkboxes.add(new Checkbox(false, "Nation"));
        checkboxes.add(new Checkbox(true, "Northern Vietnam"));
        checkboxes.add(new Checkbox(false, "Central Vietnam"));
        checkboxes.add(new Checkbox(false, "South Vietnam"));
        return checkboxes;
    }

}
