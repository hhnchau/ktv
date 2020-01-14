package com.vk2.touchsreentab.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vk2.touchsreentab.R;


public class ControlAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    public static final int CONTROL = 0;
    public static final int FUN = 1;
    private static int vol = 0, tone = 0, preset = 0;
    private int type;

    public ControlAdapter(int type) {
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (type == CONTROL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_control, parent, false);
            return new ControlViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fun, parent, false);
            return new FunViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (type == CONTROL) {
            final ControlViewHolder vh = (ControlViewHolder) viewHolder;

            vh.seekBarVol.setProgress(vol);
            vh.tvVolValue.setText(String.valueOf(vol));
            vh.seekBarTone.setProgress(tone);
            vh.tvToneValue.setText(String.valueOf(tone));
            onPreset(vh, preset);

            vh.seekBarVol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    vh.tvVolValue.setText(String.valueOf(progress));
                    volumeCallback(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            vh.seekBarTone.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    vh.tvToneValue.setText(String.valueOf(i));
                    toneCallback(i);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            vh.btnDecrVol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = Integer.parseInt(vh.tvVolValue.getText().toString());
                    i--;
                    if (i < 0) i = 0;
                    vh.tvVolValue.setText(String.valueOf(i));
                    vh.seekBarVol.setProgress(i);
                    volumeCallback(i);
                }
            });

            vh.btnIncrVol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = Integer.parseInt(vh.tvVolValue.getText().toString());
                    i++;
                    if (i > 100) i = 100;
                    vh.tvVolValue.setText(String.valueOf(i));
                    vh.seekBarVol.setProgress(i);
                    volumeCallback(i);
                }
            });


            vh.btnDecrTone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = Integer.parseInt(vh.tvToneValue.getText().toString());
                    i--;
                    if (i < 0) i = 0;
                    vh.tvToneValue.setText(String.valueOf(i));
                    vh.seekBarTone.setProgress(i);
                    toneCallback(i);
                }
            });

            vh.btnIncrTone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = Integer.parseInt(vh.tvToneValue.getText().toString());
                    i++;
                    if (i > 100) i = 100;
                    vh.tvToneValue.setText(String.valueOf(i));
                    vh.seekBarTone.setProgress(i);
                    toneCallback(i);
                }
            });

            vh.btnDefault.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPreset(vh, PRESET_DEFAULT);
                    presetCallback(PRESET_DEFAULT);
                }
            });

            vh.btnTreble.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPreset(vh, PRESET_TREBLE);
                    presetCallback(PRESET_TREBLE);
                }
            });

            vh.btnBass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPreset(vh, PRESET_BASS);
                    presetCallback(PRESET_BASS);
                }
            });


        } else {
            FunViewHolder vh = (FunViewHolder) viewHolder;
            vh.ic1.setOnClickListener(this);
            vh.ic2.setOnClickListener(this);
            vh.ic3.setOnClickListener(this);
            vh.ic4.setOnClickListener(this);
            vh.ic5.setOnClickListener(this);
            vh.ic6.setOnClickListener(this);
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ic1:
                iconFunCallback(FUN_ICON_1);
                break;
            case R.id.ic2:
                iconFunCallback(FUN_ICON_2);
                break;
            case R.id.ic3:
                iconFunCallback(FUN_ICON_3);
                break;
            case R.id.ic4:
                iconFunCallback(FUN_ICON_4);
                break;
            case R.id.ic5:
                iconFunCallback(FUN_ICON_5);
                break;
            case R.id.ic6:
                iconFunCallback(FUN_ICON_6);
                break;
        }
    }

    private void volumeCallback(int progress) {
        vol = progress;
        if (onItemClick != null)
            onItemClick.onVolume(progress);
    }

    private void toneCallback(int progress) {
        tone = progress;
        if (onItemClick != null)
            onItemClick.onTone(progress);
    }

    private void onPreset(ControlViewHolder vh, int btn) {
        if (btn == PRESET_DEFAULT) {
            vh.btnDefault.setBackgroundResource(R.drawable.custom_rounded_border_selected_button);
            vh.btnTreble.setBackgroundResource(R.drawable.custom_rounded_border_unselected_button);
            vh.btnBass.setBackgroundResource(R.drawable.custom_rounded_border_unselected_button);
        } else if (btn == PRESET_TREBLE) {
            vh.btnDefault.setBackgroundResource(R.drawable.custom_rounded_border_unselected_button);
            vh.btnTreble.setBackgroundResource(R.drawable.custom_rounded_border_selected_button);
            vh.btnBass.setBackgroundResource(R.drawable.custom_rounded_border_unselected_button);
        } else if (btn == PRESET_BASS) {
            vh.btnDefault.setBackgroundResource(R.drawable.custom_rounded_border_unselected_button);
            vh.btnTreble.setBackgroundResource(R.drawable.custom_rounded_border_unselected_button);
            vh.btnBass.setBackgroundResource(R.drawable.custom_rounded_border_selected_button);
        }
    }

    private void presetCallback(int btn) {
        preset = btn;
        if (onItemClick != null)
            onItemClick.onPreset(btn);
    }

    private void iconFunCallback(int icon) {
        if (onItemClick != null)
            onItemClick.onFun(icon);
    }

    private class ControlViewHolder extends RecyclerView.ViewHolder {

        private Button btnDefault, btnTreble, btnBass;
        private ImageView btnIncrVol, btnDecrVol, btnIncrTone, btnDecrTone;
        private SeekBar seekBarVol, seekBarTone;
        private TextView tvVolValue, tvToneValue;

        ControlViewHolder(@NonNull View view) {
            super(view);

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

        }
    }

    private class FunViewHolder extends RecyclerView.ViewHolder {
        private ImageView ic1, ic2, ic3, ic4, ic5, ic6;

        FunViewHolder(@NonNull View itemView) {
            super(itemView);
            ic1 = itemView.findViewById(R.id.ic1);
            ic2 = itemView.findViewById(R.id.ic2);
            ic3 = itemView.findViewById(R.id.ic3);
            ic4 = itemView.findViewById(R.id.ic4);
            ic5 = itemView.findViewById(R.id.ic5);
            ic6 = itemView.findViewById(R.id.ic6);
        }
    }

    public static final int PRESET_DEFAULT = 0;
    public static final int PRESET_TREBLE = 1;
    public static final int PRESET_BASS = 2;

    private static final int FUN_ICON_1 = R.mipmap.ic_praise;
    private static final int FUN_ICON_2 = R.mipmap.ic_applause;
    private static final int FUN_ICON_3 = R.mipmap.ic_flowers;
    private static final int FUN_ICON_4 = R.mipmap.ic_love;
    private static final int FUN_ICON_5 = R.mipmap.ic_speechless;
    private static final int FUN_ICON_6 = R.mipmap.ic_cheers;

    public interface OnItemClick {
        void onVolume(int progress);

        void onTone(int progress);

        void onPreset(int btn);

        void onFun(int icon);
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

}