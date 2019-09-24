package com.vk2.touchsreentab.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vk2.touchsreentab.R;

public class MyKeyboard extends LinearLayout implements View.OnClickListener {
    public static final String BTN_CLEAR = "clear";
    public static final String BTN_SPACE = "space";
    public static final String BTN_WRITE = "write";
    public static final String BTN_KEYBOARD = "keyboard";

    public MyKeyboard(Context context) {
        super(context);
        create(context);
    }

    public MyKeyboard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        create(context);
    }

    public MyKeyboard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        create(context);
    }

    private void create(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            inflater.inflate(R.layout.keyboard, this);

            this.findViewById(R.id.key0).setOnClickListener(this);
            this.findViewById(R.id.key1).setOnClickListener(this);
            this.findViewById(R.id.key2).setOnClickListener(this);
            this.findViewById(R.id.key3).setOnClickListener(this);
            this.findViewById(R.id.key4).setOnClickListener(this);
            this.findViewById(R.id.key5).setOnClickListener(this);
            this.findViewById(R.id.key6).setOnClickListener(this);
            this.findViewById(R.id.key7).setOnClickListener(this);
            this.findViewById(R.id.key8).setOnClickListener(this);
            this.findViewById(R.id.key9).setOnClickListener(this);

            this.findViewById(R.id.keyq).setOnClickListener(this);
            this.findViewById(R.id.keyw).setOnClickListener(this);
            this.findViewById(R.id.keye).setOnClickListener(this);
            this.findViewById(R.id.keyr).setOnClickListener(this);
            this.findViewById(R.id.keyt).setOnClickListener(this);
            this.findViewById(R.id.keyy).setOnClickListener(this);
            this.findViewById(R.id.keyu).setOnClickListener(this);
            this.findViewById(R.id.keyi).setOnClickListener(this);
            this.findViewById(R.id.keyo).setOnClickListener(this);
            this.findViewById(R.id.keyp).setOnClickListener(this);

            this.findViewById(R.id.keya).setOnClickListener(this);
            this.findViewById(R.id.keys).setOnClickListener(this);
            this.findViewById(R.id.keyd).setOnClickListener(this);
            this.findViewById(R.id.keyf).setOnClickListener(this);
            this.findViewById(R.id.keyg).setOnClickListener(this);
            this.findViewById(R.id.keyh).setOnClickListener(this);
            this.findViewById(R.id.keyj).setOnClickListener(this);
            this.findViewById(R.id.keyk).setOnClickListener(this);
            this.findViewById(R.id.keyl).setOnClickListener(this);

            this.findViewById(R.id.keyz).setOnClickListener(this);
            this.findViewById(R.id.keyx).setOnClickListener(this);
            this.findViewById(R.id.keyc).setOnClickListener(this);
            this.findViewById(R.id.keyv).setOnClickListener(this);
            this.findViewById(R.id.keyb).setOnClickListener(this);
            this.findViewById(R.id.keyn).setOnClickListener(this);
            this.findViewById(R.id.keym).setOnClickListener(this);
            this.findViewById(R.id.keyclear).setOnClickListener(this);

            this.findViewById(R.id.keywrite).setOnClickListener(this);
            this.findViewById(R.id.keyspace).setOnClickListener(this);
            this.findViewById(R.id.keykeyboard).setOnClickListener(this);
        }
    }

    public void show() {
        if (this.getVisibility() == View.GONE) this.setVisibility(View.VISIBLE);
    }

    public void hide() {
        if (this.getVisibility() == VISIBLE) this.setVisibility(GONE);
    }

    @Override
    public void onClick(View v) {
        String tag = v.getTag().toString();
        if (onListener != null) onListener.onKey(((TextView) v).getText(), tag);
    }

    public void setOnMyKeyboardListener(OnMyKeyboardListener onListener) {
        this.onListener = onListener;
    }

    private OnMyKeyboardListener onListener;

    public interface OnMyKeyboardListener {
        void onKey(CharSequence chr, String tag);
    }
}