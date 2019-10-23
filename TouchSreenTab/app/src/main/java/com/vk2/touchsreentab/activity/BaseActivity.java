package com.vk2.touchsreentab.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    public Fragment getFragmentByTag(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }
}
