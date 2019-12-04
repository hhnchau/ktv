package com.vk2.touchsreentab.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.activity.DualMode;
import com.vk2.touchsreentab.model.viewmodel.PlayerViewModel;

import java.io.IOException;
import java.util.Map;

public class NormalPlayerFragment extends BaseFragment implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {
    private MediaPlayer mPlayer;
    private String VIDEO_PATH;
    private  PlayerViewModel playerViewModel;

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
            VIDEO_PATH = getArguments().getString("url");
            playerListener();
        }
        return view;
    }

    private void playerListener() {
        if (getActivity() == null) return;
        playerViewModel = ViewModelProviders.of(getActivity()).get(PlayerViewModel.class);
        playerViewModel.getPlayer().observe(getActivity(), new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(Map<String, Object> stringObjectMap) {
                int action = (int) stringObjectMap.get("action");
                if (action == PlayerViewModel.ACTION_BROADCAST && mPlayer != null) {
                    playerViewModel.setValue(PlayerViewModel.ACTION_PROGRESS, mPlayer.getDuration(), mPlayer.getCurrentPosition());
                } else if (action == PlayerViewModel.ACTION_SEEK && mPlayer != null) {
                    long p = (long) stringObjectMap.get("progress");
                    mPlayer.seekTo((int) p);
                }
            }
        });
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
                if (playerViewModel != null)
                    playerViewModel.setFinish(PlayerViewModel.ACTION_FINISHED);
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
