package com.vk2.touchsreentab.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.activity.box.BoxSettingFragment;
import com.vk2.touchsreentab.fragment.ExoPlayer;
import com.vk2.touchsreentab.fragment.YoutubePlayer;

public class SingleMode extends AppCompatActivity {
    private Bundle bundle;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);
        bundle = new Bundle();
    }

    public void setting(View view) {
        BoxSettingFragment f = BoxSettingFragment.newInstance("");
        f.show(getSupportFragmentManager(), null);
    }

    public void song(View view) {
        showYoutubePlayer(getBundleId("1juIFmPyG-Y"));
    }

    public void search(View view) {
        showExoPlayer(getBundleId("https://firebasestorage.googleapis.com/v0/b/kingpes-download.appspot.com/o/video%2Fkingpes1.mp4?alt=media&token=8c7a2ef4-f644-423b-8a7b-cf4fd79ed392"));
    }

    public void showExoPlayer(@NonNull Bundle bundle) {
        getFragmentManager().beginTransaction()
                .replace(R.id.videoFrame, ExoPlayer.newInstance(bundle))
                .commit();
    }

    public void showYoutubePlayer(@NonNull Bundle bundle) {
        getFragmentManager().beginTransaction()
                .replace(R.id.videoFrame, YoutubePlayer.newInstance(bundle))
                .commit();
    }

    private Bundle getBundleId(String id) {
        bundle.clear();
        bundle.putString("id", id);
        bundle.putString("apiKey", "AIzaSyAeUyi3q4lB21g18hUnR9NWpqU8eTDYsl8");
        return bundle;
    }

    public void videoFrameClick(View view) {
        BoxSettingFragment f = BoxSettingFragment.newInstance("");
        f.show(getSupportFragmentManager(), null);
    }
}
