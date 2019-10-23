package com.vk2.touchsreentab.fragment;

import android.app.Fragment;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.vk2.touchsreentab.R;

import java.io.IOException;

public class NormalPlayer extends Fragment implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {
    private MediaPlayer mPlayer;
    private String VIDEO_PATH;

    public static NormalPlayer newInstance(@NonNull Bundle bundle) {
        NormalPlayer normalFragment = new NormalPlayer();
        normalFragment.setArguments(bundle);
        return normalFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.normal_player_fragment, container, false);
        SurfaceView surfaceView = result.findViewById(R.id.surfaceView);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        if (getArguments() != null) {
            VIDEO_PATH = getArguments().getString("id");
        }
        return result;
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mPlayer = new MediaPlayer();
        mPlayer.setDisplay(surfaceHolder);
        try {
            Uri uri = Uri.parse(VIDEO_PATH);
            mPlayer.setDataSource(getActivity(), uri);
            mPlayer.prepare();
            mPlayer.setOnPreparedListener(this);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }
}
