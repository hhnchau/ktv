package com.vk2.touchsreentab.fragment.setting;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.databinding.FragmentSettingMidiBinding;

public class FragmentMidi extends Fragment implements View.OnClickListener {
    public static final int GREY = 0;
    public static final int WHITE = 1;
    public static final int YELLOW = 2;
    public static final int RED = 3;
    public static final int GREEN = 4;
    public ObservableInt seek = new ObservableInt();
    public ObservableInt colors = new ObservableInt();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSettingMidiBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting_midi, container, false);
        binding.setDataBinding(this);
        binding.colorGrey.setOnClickListener(this);
        binding.colorWhite.setOnClickListener(this);
        binding.colorYellow.setOnClickListener(this);
        binding.colorRed.setOnClickListener(this);
        binding.colorGreen.setOnClickListener(this);
        binding.btnMinus.setOnClickListener(this);
        binding.btnPlus.setOnClickListener(this);
        binding.sticky.setSticky(getString(R.string.txt_midi_settings));
        binding.sticky.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null)
                    getActivity().onBackPressed();
            }
        });
        binding.toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seek.set(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.color_grey:
                colors.set(GREY);
                break;
            case R.id.color_white:
                colors.set(WHITE);
                break;
            case R.id.color_yellow:
                colors.set(YELLOW);
                break;
            case R.id.color_red:
                colors.set(RED);
                break;
            case R.id.color_green:
                colors.set(GREEN);
                break;
            case R.id.btn_plus:
                seek.set(seek.get() + 1 > 6 ? 6 : seek.get() + 1);
                break;
            case R.id.btn_minus:
                seek.set(seek.get() - 1 < 0 ? 0 : seek.get() - 1);
                break;
        }
    }
}
