package com.vk2.touchsreentab.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.commonsware.cwac.preso.PresentationFragment;
import com.commonsware.cwac.preso.PresentationHelper;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.adapter.ControlAdapter;
import com.vk2.touchsreentab.database.entity.Song;
import com.vk2.touchsreentab.fragment.ControlFragment;
import com.vk2.touchsreentab.fragment.ExoPlayerFragment;
import com.vk2.touchsreentab.fragment.FragmentDisplayPresentation;
import com.vk2.touchsreentab.fragment.NormalPlayer;
import com.vk2.touchsreentab.fragment.PageFragment;
import com.vk2.touchsreentab.fragment.NormalPlayerFragment;
import com.vk2.touchsreentab.fragment.YoutubePlayerFragment;
import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentcz;
import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentez;
import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentoz;
import com.vk2.touchsreentab.model.viewmodel.PlaylistModelView;

import java.util.ArrayList;
import java.util.List;

import static com.google.gson.reflect.TypeToken.get;


public class DualMode extends BaseActivity implements PresentationHelper.Listener, View.OnClickListener {
    private PlaylistModelView playlistModelView;
    private List<Song> playlist;
    private PageFragment pageFragment;
    private ControlFragment controlFragment;
    private FrameLayout framePage, frameControl;
    private PresentationHelper helper = null;
    private FragmentDisplayPresentation fragmentDisplayPresentation;
    private PresentationFragment preso;
    private View inline = null;
    private Bundle bundle;
    private ConstraintLayout constraintPlayer;
    private ConstraintLayout controlPlayer;
    private ConstraintSet constraintSet;
    private boolean isPlaying = false;
    private boolean isFull = false;
    private RecyclerView rcvControl;
    private ControlAdapter controlAdapter, funAdapter;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dual);

        playlistModelView = ViewModelProviders.of(this).get(PlaylistModelView.class);
        playlistModelView.getPlaylist().observe(this, new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                if (songs.size() == 0) {
                    onDefault();
                } else {
                    playlist = songs;
                    if (!isPlaying) onPlay();
                }
            }
        });

        initView();
        addFragment();
        addControlPlayerToBottom();
    }

    private void initView() {
        bundle = new Bundle();
        findViewById(R.id.imgControl).setOnClickListener(this);
        findViewById(R.id.imgFun).setOnClickListener(this);
        findViewById(R.id.imgFull).setOnClickListener(this);
        findViewById(R.id.imgVocal).setOnClickListener(this);
        findViewById(R.id.imgReplay).setOnClickListener(this);
        findViewById(R.id.imgPlay).setOnClickListener(this);
        findViewById(R.id.imgNext).setOnClickListener(this);
        findViewById(R.id.imgReduce).setOnClickListener(this);
        findViewById(R.id.imgAdd).setOnClickListener(this);
        rcvControl = findViewById(R.id.rcvControl);
        rcvControl.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        controlAdapter = new ControlAdapter(ControlAdapter.CONTROL);
        funAdapter = new ControlAdapter(ControlAdapter.FUN);
        framePage = findViewById(R.id.framePage);
        frameControl = findViewById(R.id.frameControl);
        constraintPlayer = findViewById(R.id.boxPlayer);
        controlPlayer = findViewById(R.id.controlPlayer);
        constraintSet = new ConstraintSet();

        helper = new PresentationHelper(this, this);
        inline = findViewById(R.id.preso);
    }

    private void addFragment() {
        if (pageFragment == null) pageFragment = new PageFragment();
        if (controlFragment == null) controlFragment = new ControlFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.framePage, pageFragment, PageFragment.class.getName())
                .add(R.id.frameControl, controlFragment, ControlFragment.class.getName())
                .commit();
    }

    public void playerFullScreen() {
        framePage.setVisibility(View.GONE);
        frameControl.setVisibility(View.GONE);
        addControlPlayerToFront();
    }

    public void playerSmallScreen() {
        framePage.setVisibility(View.VISIBLE);
        frameControl.setVisibility(View.VISIBLE);
        addControlPlayerToBottom();
    }

    @Override
    public void onResume() {
        super.onResume();
        helper.onResume();
    }

    @Override
    public void onPause() {
        helper.onPause();
        super.onPause();
    }


    @Override
    public void showPreso(Display display) {
        if (inline.getVisibility() == View.VISIBLE) {
            inline.setVisibility(View.GONE);
            if (preso != null)
                getFragmentManager().beginTransaction().remove(preso).commit();
        }
        preso = buildPreso(display);
        if (preso != null) {
            preso.show(getFragmentManager(), FragmentDisplayPresentation.class.getName());
            fragmentDisplayPresentation = (FragmentDisplayPresentation) preso;
        }
    }

    @Override
    public void clearPreso(boolean switchToInline) {
        if (switchToInline) {
            inline.setVisibility(View.VISIBLE);
            getFragmentManager().beginTransaction().add(R.id.preso, buildPreso(null), FragmentDisplayPresentation.class.getName()).commit();
        }

        if (preso != null) {
            preso.dismiss();
            preso = null;
        }
    }

    private PresentationFragment buildPreso(Display display) {
        return (FragmentDisplayPresentation.newInstance(this, display));
    }


    private void isYoutubePlayer() {
        constraintSet.clone(constraintPlayer);
        constraintSet.connect(R.id.controlFull, ConstraintSet.BOTTOM, R.id.framePlayer, ConstraintSet.TOP);
        constraintSet.connect(R.id.framePlayer, ConstraintSet.TOP, R.id.controlFull, ConstraintSet.BOTTOM);
        constraintSet.setMargin(R.id.framePlayer, ConstraintSet.BOTTOM, 60);
        constraintSet.applyTo(constraintPlayer);
    }

    private void noneYoutubePlayer() {
        constraintSet.clone(constraintPlayer);
        constraintSet.connect(R.id.controlFull, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.connect(R.id.framePlayer, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.setMargin(R.id.framePlayer, ConstraintSet.BOTTOM, 0);
        constraintSet.applyTo(constraintPlayer);
    }


    private void addControlPlayerToBottom() {
        constraintSet.clone(constraintPlayer);
        constraintSet.connect(R.id.framePlayer, ConstraintSet.BOTTOM, R.id.controlPlayer, ConstraintSet.TOP);
        constraintSet.connect(R.id.controlPlayer, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.connect(R.id.controlPlayer, ConstraintSet.TOP, R.id.framePlayer, ConstraintSet.BOTTOM);
        constraintSet.connect(R.id.controlPlayer, ConstraintSet.START, R.id.framePlayer, ConstraintSet.START);
        constraintSet.connect(R.id.controlPlayer, ConstraintSet.END, R.id.framePlayer, ConstraintSet.END);
        constraintSet.applyTo(constraintPlayer);
        controlPlayer.setBackground(null);
    }

    private void addControlPlayerToFront() {
        constraintSet.clone(constraintPlayer);
        constraintSet.connect(R.id.framePlayer, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.connect(R.id.controlPlayer, ConstraintSet.BOTTOM, R.id.framePlayer, ConstraintSet.BOTTOM);
        constraintSet.connect(R.id.controlPlayer, ConstraintSet.TOP, R.id.framePlayer, ConstraintSet.TOP);
        constraintSet.connect(R.id.controlPlayer, ConstraintSet.START, R.id.l1, ConstraintSet.START);
        constraintSet.connect(R.id.controlPlayer, ConstraintSet.END, R.id.l2, ConstraintSet.END);
        constraintSet.setVerticalBias(R.id.controlPlayer, 0.98f);
        constraintSet.applyTo(constraintPlayer);
        controlPlayer.setBackground(getResources().getDrawable(R.drawable.bg_control_player));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgFull:
                if (isFull) playerSmallScreen();
                else playerFullScreen();
                isFull = !isFull;
                break;
            case R.id.imgControl:
                rcvControl.setAdapter(controlAdapter);
                break;
            case R.id.imgFun:
                rcvControl.setAdapter(funAdapter);
                break;

            case R.id.imgVocal:

                break;
            case R.id.imgReplay:
                onPlay();
                break;
            case R.id.imgPlay:

                break;
            case R.id.imgNext:
                onFinished();
                break;
            case R.id.imgReduce:
                break;
            case R.id.imgAdd:
                if (fragmentDisplayPresentation != null) {
                    isFull = !isFull;
                    fragmentDisplayPresentation.showAdsFragment(isFull);
                }
                break;
        }
    }

    public void addPlayerFragment(final Fragmentez frgez, final String id) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = getBundleId(id);
                if (frgez == Fragmentez.NORMAL_PLAYER_FRAGMENT) {
                    showNormalPlayer(bundle);
                    if (preso != null && fragmentDisplayPresentation != null)
                        fragmentDisplayPresentation.showNormalPlayer(bundle);
                } else if (frgez == Fragmentez.EXO_PLAYER_FRAGMENT) {
                    showExoPlayer(bundle);
                    if (preso != null && fragmentDisplayPresentation != null)
                        fragmentDisplayPresentation.showExoPlayer(bundle);
                } else if (frgez == Fragmentez.YOUTUBE_PLAYER_FRAGMENT) {
                    showYoutubePlayer(bundle);
                    if (preso != null && fragmentDisplayPresentation != null)
                        fragmentDisplayPresentation.showYoutubePlayer(bundle);
                }
            }
        });
    }

    private void showNormalPlayer(@NonNull Bundle bundle) {
        NormalPlayerFragment normalPlayerFragment = NormalPlayerFragment.newInstance(bundle);
        if (normalPlayerFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .detach(normalPlayerFragment)
                    .attach(normalPlayerFragment)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framePlayer, normalPlayerFragment)
                    .commit();
        }
    }

    private void showExoPlayer(@NonNull Bundle bundle) {
        ExoPlayerFragment exoPlayerFragment = ExoPlayerFragment.newInstance(bundle);
        if (exoPlayerFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .detach(exoPlayerFragment)
                    .attach(exoPlayerFragment)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framePlayer, exoPlayerFragment)
                    .commit();
        }
    }

    private void showYoutubePlayer(@NonNull Bundle bundle) {
        YoutubePlayerFragment youTubePlayerFragment = YoutubePlayerFragment.newInstance(bundle);
        if (youTubePlayerFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .detach(youTubePlayerFragment)
                    .attach(youTubePlayerFragment)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framePlayer, youTubePlayerFragment)
                    .commit();
        }
    }

    private Bundle getBundleId(String id) {
        bundle.clear();
        bundle.putString("id", id);
        bundle.putString("apiKey", "AIzaSyAeUyi3q4lB21g18hUnR9NWpqU8eTDYsl8");
        return bundle;
    }

    public void onDefault() {
        isPlaying = false;
        addPlayerFragment(Fragmentez.NORMAL_PLAYER_FRAGMENT, "android.resource://" + getPackageName() + "/" + R.raw.buon_khong_em);
    }

    private void onPlay() {
        isPlaying = true;
        if (playlist != null && playlist.size() > 0)
            addPlayerFragment(getVideoType(playlist.get(0).getSongName()), playlist.get(0).getSongName());
    }

    public void onFinished() {
        isPlaying = false;
        if (playlist != null && playlist.size() > 0)
            playlistModelView.setValue(this, playlist.get(0), PlaylistModelView.REMOVE);
        else
            onDefault();
    }

    private Fragmentez getVideoType(String id) {
        //Check Video Type
        return Fragmentez.NORMAL_PLAYER_FRAGMENT;
    }
}
