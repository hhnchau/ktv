package com.vk2.touchsreentab.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

public class MyAnimator {
    private static MyAnimator instance = null;

    public static MyAnimator getInstance() {
        if (instance == null) {
            instance = new MyAnimator();
        }
        return instance;
    }

    private int duration = 500;
    private float scale = 0.8f;

    public void blinking(View view) {
        ObjectAnimator scaleXDownAnimator = ObjectAnimator.ofFloat(view, "scaleX", scale);
        scaleXDownAnimator.setRepeatMode(ValueAnimator.REVERSE);
        scaleXDownAnimator.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator scaleYDownAnimator = ObjectAnimator.ofFloat(view, "scaleY", scale);
        scaleYDownAnimator.setRepeatMode(ValueAnimator.REVERSE);
        scaleYDownAnimator.setRepeatCount(ValueAnimator.INFINITE);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(duration);
        set.setInterpolator(new AccelerateInterpolator());
        set.playTogether(scaleXDownAnimator, scaleYDownAnimator);

        set.cancel();
        set.start();
    }

    public void translate(View view){
        ObjectAnimator translateXAnimator = ObjectAnimator.ofFloat(view, "translationX", 100);
        //translateXAnimator.setRepeatMode(ValueAnimator.REVERSE);
        //translateXAnimator.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator translateYAnimator = ObjectAnimator.ofFloat(view, "translationY", 0);
        //translateYAnimator.setRepeatMode(ValueAnimator.REVERSE);
        //translateYAnimator.setRepeatCount(ValueAnimator.INFINITE);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(duration);
        set.setInterpolator(new AccelerateInterpolator());
        set.playTogether(translateXAnimator, translateYAnimator);

        set.cancel();
        set.start();
    }

    public void alpha(View view){
        ObjectAnimator alphaDownAnimator = ObjectAnimator.ofFloat(view, "alpha", 0.5f);
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new AccelerateInterpolator());
        set.playTogether(alphaDownAnimator);

        set.cancel();
        set.start();

    }

}