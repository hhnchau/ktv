package com.vk2.touchsreentab.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.api.ApiController;

import java.util.ArrayList;
import java.util.List;

public class FragmentBoxScreen extends Fragment implements YouTubePlayer.OnInitializedListener  {

    private FrameLayout mFramelayoutYoutube;
    //youtubePlayer
    private YouTubePlayerFragment youTubePlayerFragment;
    private YouTubePlayer youTubePlayerYoutube;
    private boolean wasRestored;
    private List<String> playList;
    private int position;
    private View view;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        view= inflater.inflate(R.layout.fragment_box, container, false);
        initData();
        initView();
        return view;
    }


    private void initData() {
        position = 0;
        playList = new ArrayList<>();
        playList.add("lK4Eb6rhuG8");
        playList.add("rRKaGApaWBU");
        playList.add("wnzG-lN9w_Q");
        playList.add("ftfA2OJcRF4");
        playList.add("m2sa6VcmGW0");
        playList.add("UYXIGXaGffQ");
        playList.add("kLu0D0r1yqA");
        playList.add("DuFHaVJpcr4");
        playList.add("A_HekkBbd1M");
        playList.add("dDaJL5EHOOk");
        playList.add("O6mizTWwmTQ");
        playList.add("kFRmewT9rWc");
    }
    private void initView() {
        if(ApiController.checkInternet(getActivity())){
            initPlayYoutube(true);
        }else{
            Toast.makeText(getActivity(), "No connection internet!",
                    Toast.LENGTH_LONG).show();
        }

    }
    private void initPlayYoutube(final boolean isShowYoutubePlayer) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mFramelayoutYoutube == null)
                    mFramelayoutYoutube =  view.findViewById(R.id.layout_container_youtube);
                if (isShowYoutubePlayer) {
                    mFramelayoutYoutube.setVisibility(View.VISIBLE);
                    if (youTubePlayerFragment == null)
                        youTubePlayerFragment = (YouTubePlayerFragment) (getChildFragmentManager().findFragmentById(R.id.youtube_fragment));
                    if(youTubePlayerFragment != null) {
                      //  youTubePlayerFragment.setRetainInstance(true);
                        youTubePlayerFragment.initialize("AIzaSyAeUyi3q4lB21g18hUnR9NWpqU8eTDYsl8", FragmentBoxScreen.this);
                    }else {
                        Log.e("nhu", "initPlayYoutube NULL");
                    }
                    Log.e("nhu", "initPlayYoutube initialize");
                } else {
                    mFramelayoutYoutube.setVisibility(View.GONE);
                }
                if(position < playList.size())
                    position ++;
                else {
                    position = 0;
                }
            }
        });


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTube, boolean wasRestored) {
        this.youTubePlayerYoutube = youTube;
        this.wasRestored = wasRestored;
        if (!wasRestored) {

            if (!TextUtils.isEmpty(playList.get(position))) {
                try {
                    youTubePlayerYoutube.loadVideo(playList.get(position));
                } catch (IllegalStateException e) {
                    Log.e("nhu", "PLAY YOUTUBE loadVideo videoId: " + playList.get(position) + " " + e.getMessage());
                    initPlayYoutube(false);
                }
                youTubePlayerYoutube.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                youTubePlayerYoutube.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {
                        Log.d("TAG", "onLoading");
                    }

                    @Override
                    public void onLoaded(String s) {
                        Log.d("TAG", "onLoaded://" + s);
                    }

                    @Override
                    public void onAdStarted() {
                        Log.d("TAG", "onAdStarted");
                    }

                    @Override
                    public void onVideoStarted() {
                        Log.d("TAG", "onVideoStarted");

                    }

                    @Override
                    public void onVideoEnded() {
                        Log.d("TAG", "onVideoEnded");
                        mFramelayoutYoutube.setVisibility(View.GONE);
                        onActionPlaySong(false, false);
                    }

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {
                        mFramelayoutYoutube.setVisibility(View.GONE);
                        Log.e("nhu", "PLAY YOUTUBE onError: " + errorReason.name());
                        Log.d("TAG", "onError://" + errorReason.name());
                        switch (errorReason) {
                            case USER_DECLINED_HIGH_BANDWIDTH:
                                Log.e("nhu", "PLAY YOUTUBE onError: Y-bandwith ");
                                onActionPlaySong(false, false);
                                break;
                            case USER_DECLINED_RESTRICTED_CONTENT:
                                Log.e("nhu", "Y-restrict ");
                                onActionPlaySong(false, false);
                                break;
                            case UNEXPECTED_SERVICE_DISCONNECTION:
                                Log.e("nhu", "PLAY YOUTUBE onError: Y-API ");
                                onActionPlaySong(false, false);
                                break;
                            case PLAYER_VIEW_NOT_VISIBLE:
                                Log.e("nhu", "PLAY YOUTUBE onError: Y-view ");
//                                showToast("Lỗi Y-008");
                                onActionPlaySong(false, false);
                                break;
                            case PLAYER_VIEW_TOO_SMALL:
                                Log.e("nhu", "PLAY YOUTUBE onError: Y-small ");
//                                showToast("Lỗi Y-009");
                                onActionPlaySong(false, false);
                                break;
                            case UNAUTHORIZED_OVERLAY:
                                Log.e("nhu", "PLAY YOUTUBE onError: Y-over ");
//                                showToast("Lỗi Y-010");
                                onActionPlaySong(false, false);
                                break;
                            case INTERNAL_ERROR:
                                Log.e("nhu", "PLAY YOUTUBE onError: Y-internal");
//                                showToast("Y-internal");
                                onActionPlaySong(false, false);
                                break;
                            case NETWORK_ERROR:
                                Log.e("nhu", "PLAY YOUTUBE onError:I400 ");
//                                showToast("Lỗi Y-006");
                                onActionPlaySong(false, false);
                                break;
                            case NOT_PLAYABLE:
                                Log.e("nhu", "PLAY YOUTUBE onError: Y-video ");
                                onActionPlaySong(false, false);
                                break;
                            case UNKNOWN:
                                Log.e("nhu", "PLAY YOUTUBE onError: Y-UNKNOWN ");
                                onActionPlaySong(false, false);
                                break;
                            default:
                                onActionPlaySong(false, false);
                                break;

                        }

                    }
                });
                youTubePlayerYoutube.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
                    @Override
                    public void onPlaying() {
                    }

                    @Override
                    public void onPaused() {

                    }

                    @Override
                    public void onStopped() {

                    }

                    @Override
                    public void onBuffering(boolean b) {

                    }

                    @Override
                    public void onSeekTo(int i) {

                    }
                });
            }
        } else {
            onActionPlaySong(false, false);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(getActivity(), "Play youtube error!",
                Toast.LENGTH_LONG).show();
    }
    private void releaseYoutubePlayer() {
        if (youTubePlayerYoutube != null) {
            Log.e("nhu", "PLAY YOUTUBE onActionNextSong release");
            if (mFramelayoutYoutube != null)
                mFramelayoutYoutube.setVisibility(View.GONE);
            youTubePlayerYoutube.release();
            youTubePlayerYoutube = null;
            youTubePlayerFragment = null;
            mFramelayoutYoutube = null;
        }

    }
    public boolean onActionPlaySong(final boolean isReplay, final boolean isVideoError) {
        releaseYoutubePlayer();
        initPlayYoutube(true);
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseYoutubePlayer();
    }
}
