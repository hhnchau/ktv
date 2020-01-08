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
import android.widget.SeekBar;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.databinding.FragmentSettingSoundLightBinding;

public class FragmentSoundLight extends Fragment implements View.OnClickListener {
    private static final int STEP = 5;
    public ObservableInt tone = new ObservableInt();
    public ObservableInt progressMusic = new ObservableInt();
    public ObservableInt progressTone = new ObservableInt();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSettingSoundLightBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting_sound_light, container, false);
        binding.setDataBinding(this);
        binding.btnMinusMusic.setOnClickListener(this);
        binding.btnPlusMusic.setOnClickListener(this);
        binding.btnMinusTone.setOnClickListener(this);
        binding.btnPlusTone.setOnClickListener(this);
        binding.toneDefault.setOnClickListener(this);
        binding.toneMaleVoice.setOnClickListener(this);
        binding.toneFemaleVoice.setOnClickListener(this);
        binding.toneTreble.setOnClickListener(this);
        binding.toneBass.setOnClickListener(this);
        binding.lightIntelligentOn.setOnClickListener(this);
        binding.lightIntelligentOff.setOnClickListener(this);
        binding.lightFullOn.setOnClickListener(this);
        binding.lightFullOff.setOnClickListener(this);
        binding.lightClearanceOn.setOnClickListener(this);
        binding.lightClearanceOff.setOnClickListener(this);
        binding.seekBarMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressMusic.set(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.seekBarTone.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressTone.set(i);
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
            case R.id.btn_minus_music:
                progressMusic.set(progressMusic.get() - STEP < 0 ? 0 : progressMusic.get() - STEP);
                break;
            case R.id.btn_plus_music:
                progressMusic.set(progressMusic.get() + STEP > 100 ? 100 : progressMusic.get() + STEP);
                break;
            case R.id.btn_minus_tone:
                progressTone.set(progressTone.get() - STEP < 0 ? 0 : progressTone.get() - STEP);
                break;
            case R.id.btn_plus_tone:
                progressTone.set(progressTone.get() + STEP > 100 ? 100 : progressTone.get() + STEP);
                break;
            case R.id.tone_default:
                tone.set(0);
                break;
            case R.id.tone_male_voice:
                tone.set(1);
                break;
            case R.id.tone_female_voice:
                tone.set(2);
                break;
            case R.id.tone_treble:
                tone.set(3);
                break;
            case R.id.tone_bass:
                tone.set(4);
                break;
            case R.id.light_intelligent_on:
                break;
            case R.id.light_intelligent_off:
                break;
            case R.id.light_full_on:
                break;
            case R.id.light_full_off:
                break;
            case R.id.light_clearance_on:
                break;
            case R.id.light_clearance_off:
                break;
        }
    }
}
