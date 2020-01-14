package com.vk2.touchsreentab.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.vk2.touchsreentab.R;

public class MyNumPad extends LinearLayout implements View.OnClickListener {
    public interface OnNumPadListener {
        void onNumber(String key);

        void onButton(boolean yes);
    }

    private OnNumPadListener listener;

    public void setListener(OnNumPadListener listener) {
        this.listener = listener;
    }

    public MyNumPad(Context context) {
        super(context);
        create(context);
    }

    public MyNumPad(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        create(context);
    }

    public MyNumPad(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        create(context);
    }

    private void create(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            inflater.inflate(R.layout.keyboard_numpad, this);

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
            this.findViewById(R.id.no).setOnClickListener(this);
            this.findViewById(R.id.yes).setOnClickListener(this);
        }
    }

    public void show() {
        if (this.getVisibility() == View.GONE) this.setVisibility(View.VISIBLE);
    }

    public void hide() {
        if (this.getVisibility() == VISIBLE) this.setVisibility(GONE);
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            if (view.getId() == R.id.no)
                listener.onButton(false);
            else if (view.getId() == R.id.yes)
                listener.onButton(true);
            else
                listener.onNumber(view.getTag().toString());
    }
}