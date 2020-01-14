package com.vk2.touchsreentab.activity;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.adapter.SettingMenuAdapter;
import com.vk2.touchsreentab.databinding.ActivitySettingBinding;
import com.vk2.touchsreentab.fragment.setting.FragmentAbout;
import com.vk2.touchsreentab.fragment.setting.FragmentAdmin;
import com.vk2.touchsreentab.fragment.setting.FragmentBroadcastManagement;
import com.vk2.touchsreentab.fragment.setting.FragmentBroadcastSongs;
import com.vk2.touchsreentab.fragment.setting.FragmentChangePassword;
import com.vk2.touchsreentab.fragment.setting.FragmentGeneral;
import com.vk2.touchsreentab.fragment.setting.FragmentLanguage;
import com.vk2.touchsreentab.fragment.setting.FragmentMidi;
import com.vk2.touchsreentab.fragment.setting.FragmentMusic;
import com.vk2.touchsreentab.fragment.setting.FragmentNetwork;
import com.vk2.touchsreentab.fragment.setting.FragmentReboot;
import com.vk2.touchsreentab.fragment.setting.FragmentSongManagement;
import com.vk2.touchsreentab.fragment.setting.FragmentSoundLight;
import com.vk2.touchsreentab.fragment.setting.FragmentSubtitle;
import com.vk2.touchsreentab.fragment.setting.FragmentSystem;
import com.vk2.touchsreentab.model.setting.Menu;
import com.vk2.touchsreentab.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends BaseActivity {
    private static final int FRAGMENT_LANGUAGE = 0;
    private static final int FRAGMENT_SOUND_LIGHT = 1;
    private static final int FRAGMENT_GENERAL = 2;
    private static final int FRAGMENT_NETWORK = 3;
    private static final int FRAGMENT_MUSIC = 4;
    private static final int FRAGMENT_ADMIN = 5;
    private static final int FRAGMENT_SYSTEM = 6;
    private static final int FRAGMENT_REBOOT = 7;

    private SettingMenuAdapter adapter;
    private int position;

    public ObservableBoolean cloud = Utils.isCloud();
    public ObservableBoolean nas = new ObservableBoolean();
    public ObservableBoolean usb = new ObservableBoolean();
    public ObservableBoolean disk = new ObservableBoolean();
    public ObservableBoolean light = Utils.isCloud();
    public ObservableBoolean bluetooth = Utils.isCloud();
    public ObservableBoolean network = new ObservableBoolean();
    public ObservableBoolean lock =Utils.isCloud();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySettingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        binding.statusBar.setDataBinding(this);
        binding.statusBar.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        int[] icons = {
                R.mipmap.ic_language,
                R.mipmap.ic_sound_light,
                R.mipmap.ic_general,
                R.mipmap.ic_network,
                R.mipmap.ic_music,
                R.mipmap.ic_administrator,
                R.mipmap.ic_system,
                R.mipmap.ic_off
        };
        String[] titles = {
                getResources().getString(R.string.setting_language),
                getResources().getString(R.string.setting_sound_light),
                getResources().getString(R.string.setting_general),
                getResources().getString(R.string.setting_network),
                getResources().getString(R.string.setting_music),
                getResources().getString(R.string.setting_administrator),
                getResources().getString(R.string.setting_system),
                getResources().getString(R.string.setting_off)
        };

        List<Menu> menus = new ArrayList<>();
        for (int i = 0; i < icons.length; i++) {
            menus.add(new Menu(
                    false,
                    titles[i],
                    "",
                    icons[i]
            ));
        }

        adapter = new SettingMenuAdapter(menus) {
            @Override
            public void OnItemClickListener(int p) {
                position = p;
                showFragment(p);
            }
        };
        binding.rcv.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rcv.setLayoutManager(layoutManager);
        binding.rcv.addItemDecoration(getDivider(layoutManager));
    }

    public void updateStatus(String status) {
        if (adapter != null) {
            adapter.updateStatus(position, status);
        }
    }

    private DividerItemDecoration getDivider(LinearLayoutManager layoutManager) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(this.getResources().getDrawable(R.drawable.divider));
        return dividerItemDecoration;
    }

    private void showFragment(int p) {
        switch (p) {
            case FRAGMENT_LANGUAGE:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new FragmentLanguage())
                        .commit();
                break;
            case FRAGMENT_SOUND_LIGHT:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new FragmentSoundLight())
                        .commit();
                break;
            case FRAGMENT_GENERAL:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new FragmentGeneral())
                        .commit();
                break;
            case FRAGMENT_NETWORK:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new FragmentNetwork())
                        .commit();
                break;
            case FRAGMENT_MUSIC:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new FragmentMusic())
                        .commit();
                break;
            case FRAGMENT_ADMIN:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new FragmentAdmin())
                        .commit();
                break;
            case FRAGMENT_SYSTEM:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new FragmentSystem())
                        .commit();
                break;
            case FRAGMENT_REBOOT:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new FragmentReboot())
                        .commit();
                break;
        }
    }

    public void showFragmentSongManagement() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, new FragmentSongManagement())
                .addToBackStack(FragmentSongManagement.class.getName())
                .commit();
    }

    public void showFragmentChangePassword() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, new FragmentChangePassword())
                .addToBackStack(FragmentChangePassword.class.getName())
                .commit();
    }

    public void showFragmentBroadcastManagement() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, new FragmentBroadcastManagement())
                .addToBackStack(FragmentBroadcastManagement.class.getName())
                .commit();
    }

    public void showFragmentBroadcastSongs() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, new FragmentBroadcastSongs())
                .addToBackStack(FragmentBroadcastSongs.class.getName())
                .commit();
    }

    public void showFragmentSubtitle() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, new FragmentSubtitle())
                .addToBackStack(FragmentSubtitle.class.getName())
                .commit();
    }

    public void showFragmentMidi() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, new FragmentMidi())
                .addToBackStack(FragmentMidi.class.getName())
                .commit();
    }

    public void showFragmentAbout() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, new FragmentAbout())
                .addToBackStack(FragmentAbout.class.getName())
                .commit();
    }

}
