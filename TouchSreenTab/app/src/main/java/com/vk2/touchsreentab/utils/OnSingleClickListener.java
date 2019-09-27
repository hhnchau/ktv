package com.vk2.touchsreentab.utils;

import android.os.SystemClock;
import android.view.View;

public abstract class OnSingleClickListener implements View.OnClickListener {
    private static final int INTERVAL = 600;
    private long lastClickTime;
    private boolean clickable = true;

    public abstract void onSingleClick(View v);

    @Override
    public void onClick(View view) {
//        long current = SystemClock.uptimeMillis();
//        long elapsed = current - lastClickTime;
//        lastClickTime = current;
//        if (elapsed <= INTERVAL) return;
//        onSingleClick(view);


        if (clickable) {
            clickable = false;
            onSingleClick(view);
        }
    }

    public void enable() {
        this.clickable = true;
    }
}
