package com.vk2.touchsreentab.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.activity.MainActivity;

public class PlayerFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private LinearLayout layoutSetting;
    private ImageView imgArrow;
    private Button btnDefault, btnTreble, btnBass;
    private ImageView btnIncrVol, btnDecrVol, btnIncrTone, btnDecrTone;
    private SeekBar seekBarVol, seekBarTone;
    private TextView tvVolValue, tvToneValue;
//
    private int toneValue, volValue;
    private int currentTonePresetValue = 0;
    private final int TONE_PRESET_DEFAULT = 0;
    private final int TONE_PRESET_TREBLE = 1;
    private final int TONE_PRESET_BASS = 2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_player, container, false);
        initView(view);
        return view;
    }
    private void initView(View view){
        imgArrow = view.findViewById(R.id.img_arrow);
        layoutSetting = view.findViewById(R.id.layout_setting);

        btnBass = view.findViewById(R.id.btn_tone_preset_bass);
        btnDefault = view.findViewById(R.id.btn_tone_preset_default);
        btnTreble = view.findViewById(R.id.btn_tone_preset_treble);
        btnIncrVol = view.findViewById(R.id.btn_incr_vol);
        btnDecrVol = view.findViewById(R.id.btn_decr_vol);
        btnIncrTone = view.findViewById(R.id.btn_incr_tone);
        btnDecrTone = view.findViewById(R.id.btn_decr_tone);
        seekBarVol = view.findViewById(R.id.seekbar_vol);
        seekBarTone = view.findViewById(R.id.seekbar_tone);
        tvVolValue = view.findViewById(R.id.tv_vol_value);
        tvToneValue = view.findViewById(R.id.tv_tone_value);
//
        btnIncrVol.setOnClickListener(this);
        btnDecrVol.setOnClickListener(this);
        btnIncrTone.setOnClickListener(this);
        btnDecrTone.setOnClickListener(this);
        btnDefault.setOnClickListener(this);
        btnTreble.setOnClickListener(this);
        btnBass.setOnClickListener(this);
//
        view.findViewById(R.id.zoom).setOnClickListener(this);
        view.findViewById(R.id.control).setOnClickListener(this);
        view.findViewById(R.id.feeling).setOnClickListener(this);
//  config seekbar vol
        seekBarVol.setMax(100);
        volValue = seekBarVol.getProgress();
        seekBarVol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volValue = progress;
                tvVolValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
//  config seekbar tone
        seekBarTone.setMax(4);
        toneValue = seekBarTone.getProgress();
        seekBarTone.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                toneValue = progress;
                tvToneValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    int count = 0;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.zoom:
                if(count % 2 == 0 ){
                    ((MainActivity) getActivity()).playerFullScreen();
                }else {
                    ((MainActivity) getActivity()).playerSmallScreen();
                }
                count ++;
                break;
            case R.id.control:
                if (layoutSetting.getVisibility() == View.GONE){
                    layoutSetting.setVisibility(View.VISIBLE);
                    imgArrow.setVisibility(View.VISIBLE);
                }
                else {
                    layoutSetting.setVisibility(View.GONE);
                    imgArrow.setVisibility(View.GONE);
                }
                break;
            case R.id.feeling:
                break;
            case R.id.btn_incr_vol:{
                if (volValue < 100){
                    volValue++;
                    seekBarVol.setProgress(volValue);
                    tvVolValue.setText(String.valueOf(volValue));
                }
                break;
            }
            case R.id.btn_decr_vol:{
                if (volValue > 0){
                    volValue--;
                    seekBarVol.setProgress(volValue);
                    tvVolValue.setText(String.valueOf(volValue));
                }
                break;
            }
            case R.id.btn_incr_tone:{
                if (toneValue < 4){
                    toneValue++;
                    seekBarTone.setProgress(toneValue);
                    tvToneValue.setText(String.valueOf(toneValue));
                }
                break;
            }
            case R.id.btn_decr_tone:{
                if (toneValue > 0){
                    toneValue--;
                    seekBarTone.setProgress(toneValue);
                    tvToneValue.setText(String.valueOf(toneValue));
                }
                break;
            }
            case R.id.btn_tone_preset_default:{
                checkTonePresetValue(TONE_PRESET_DEFAULT);
                break;
            }
            case R.id.btn_tone_preset_treble:{
                checkTonePresetValue(TONE_PRESET_TREBLE);
                break;
            }
            case R.id.btn_tone_preset_bass:{
                checkTonePresetValue(TONE_PRESET_BASS);
                break;
            }
        }
    }
// default là TONE_PRESET_DEFAULT = 0, check
    private void checkTonePresetValue(int tonePresetValue){
        if (this.currentTonePresetValue == tonePresetValue){
            return;
        }
//      set lại background unselect cho button
        switch (this.currentTonePresetValue){
            case TONE_PRESET_DEFAULT:{
                btnDefault.setBackgroundResource(R.drawable.custom_rounded_border_unselected_button);
                break;
            }
            case TONE_PRESET_TREBLE:{
                btnTreble.setBackgroundResource(R.drawable.custom_rounded_border_unselected_button);
                break;
            }
            case TONE_PRESET_BASS:{
                btnBass.setBackgroundResource(R.drawable.custom_rounded_border_unselected_button);
                break;
            }
        }
//        xử lý sự kiện click button ở đây
        this.currentTonePresetValue = tonePresetValue;
//        selected
        switch (tonePresetValue){
            case TONE_PRESET_DEFAULT:{
                btnDefault.setBackgroundResource(R.drawable.custom_rounded_border_selected_button);
                break;
            }
            case TONE_PRESET_TREBLE:{
                btnTreble.setBackgroundResource(R.drawable.custom_rounded_border_selected_button);
                break;
            }
            case TONE_PRESET_BASS:{
                btnBass.setBackgroundResource(R.drawable.custom_rounded_border_selected_button);
                break;
            }
        }
    }
}