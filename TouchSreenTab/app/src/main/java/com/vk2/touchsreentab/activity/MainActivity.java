package com.vk2.touchsreentab.activity;import android.content.Context;import android.os.Bundle;import android.support.v7.app.AppCompatActivity;import android.text.TextUtils;import android.util.Log;import android.view.View;import android.widget.FrameLayout;import com.vk2.touchsreentab.api.Api;import com.vk2.touchsreentab.fragment.ControlFragment;import com.vk2.touchsreentab.fragment.PageFragment;import com.vk2.touchsreentab.fragment.PlayerFragment;import com.vk2.touchsreentab.R;import com.vk2.touchsreentab.model.YouTubeApiObject;import com.vk2.touchsreentab.setting.SettingManager;import com.vk2.touchsreentab.utils.SaveDataUtils;import io.reactivex.Observer;import io.reactivex.android.schedulers.AndroidSchedulers;import io.reactivex.disposables.Disposable;import io.reactivex.schedulers.Schedulers;public class MainActivity extends AppCompatActivity {    private PageFragment pageFragment;    private PlayerFragment playerFragment;    private ControlFragment controlFragment;    private FrameLayout framePage, frameControl;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);        framePage = findViewById(R.id.framePage);        frameControl = findViewById(R.id.frameControl);        addFragment();    }    private void addFragment() {        if (pageFragment == null) pageFragment = new PageFragment();        if (playerFragment == null) playerFragment = new PlayerFragment();        if (controlFragment == null) controlFragment = new ControlFragment();        getSupportFragmentManager().beginTransaction()                .add(R.id.framePage, pageFragment, PageFragment.class.getName())                .add(R.id.framePlayer, playerFragment, PlayerFragment.class.getName())                .add(R.id.frameControl, controlFragment, ControlFragment.class.getName())                .commit();    }    public void playerFullScreen() {        framePage.setVisibility(View.GONE);        frameControl.setVisibility(View.GONE);    }    public void playerSmallScreen() {        framePage.setVisibility(View.VISIBLE);        frameControl.setVisibility(View.VISIBLE);    }}