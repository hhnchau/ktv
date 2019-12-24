package com.vk2.touchsreentab.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.activity.box.BoxHomeFragment;
import com.vk2.touchsreentab.fragment.ExoPlayerFragment;

public class SingleMode extends BaseActivity {
    private ExoPlayerFragment exoPlayerFragment;
    private Bundle bundle;
    private Handler handler;
    private Runnable runnable;
    private boolean isShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        bundle = new Bundle();
        handler = new Handler();
        showExoPlayer(getBundleId("https://firebasestorage.googleapis.com/v0/b/kingpes-download.appspot.com/o/video%2Fkingpes1.mp4?alt=media&token=8c7a2ef4-f644-423b-8a7b-cf4fd79ed392"));


        runnable = new Runnable() {
            @Override
            public void run() {
                removeFragment();
            }
        };
    }

    public void removeHandler() {
        if (handler != null) handler.removeCallbacks(runnable);
    }

    private void showHomeFragment() {
        isShow = true;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, BoxHomeFragment.newInstance(null))
                .commit();
    }

    private void removeFragment() {
        isShow = false;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment != exoPlayerFragment)
                transaction.remove(fragment);
        }
        transaction.commit();

    }


    private void showExoPlayer(@NonNull Bundle bundle) {
        exoPlayerFragment = ExoPlayerFragment.newInstance(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framePlayer, exoPlayerFragment)
                .commit();
    }


    private Bundle getBundleId(String id) {
        bundle.clear();
        bundle.putString("url", id);
        return bundle;
    }

    public void onClickRootView(View view) {
        if (!isShow) {
            showHomeFragment();
            handler.postDelayed(runnable, 5000);
        } else {
            removeFragment();
            removeHandler();
        }
    }
}
