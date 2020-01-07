package com.vk2.touchsreentab.utils;

import android.view.View;

public abstract class OnDoubleClickListener implements View.OnClickListener {
    private static final int INTERVAL = 400;
    private long lastClickTime;

    public abstract void onDoubleClick(View v);
    public abstract void onSingleClick(View v);

    @Override
    public void onClick(View view) {
        long currentClickTime = System.currentTimeMillis();
        if (currentClickTime - lastClickTime <= INTERVAL)
        {
            onDoubleClick(view);
        }
        else
        {
            lastClickTime = System.currentTimeMillis();
            onSingleClick(view);
        }
    }
}
