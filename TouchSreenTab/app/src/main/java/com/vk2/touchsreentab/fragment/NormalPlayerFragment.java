package com.vk2.touchsreentab.fragment;

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
import com.vk2.touchsreentab.activity.DualMode;

import java.io.IOException;

public class NormalPlayerFragment extends BaseFragment implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {
    private MediaPlayer mPlayer;
    private String VIDEO_PATH;

    public static NormalPlayerFragment newInstance(@NonNull Bundle bundle) {
        NormalPlayerFragment normalFragment = new NormalPlayerFragment();
        normalFragment.setArguments(bundle);
        return normalFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.normal_player_fragment, container, false);
        SurfaceView surfaceView = view.findViewById(R.id.surfaceView);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        if (getArguments() != null) {
            VIDEO_PATH = getArguments().getString("id");
        }
        return view;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (getActivity() == null) return;
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
        }
        mPlayer.setDisplay(surfaceHolder);
        mPlayer.setVolume(0, 0);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (getActivity() != null)
                    ((DualMode) getActivity()).onFinished();
            }
        });

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
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
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
}
