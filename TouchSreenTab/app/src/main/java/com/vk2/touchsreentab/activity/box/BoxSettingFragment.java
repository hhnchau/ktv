package com.vk2.touchsreentab.activity.box;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk2.touchsreentab.R;

public class BoxSettingFragment extends BaseFragmentBox {

    public static BoxSettingFragment newInstance(String data) {
        BoxSettingFragment setting = new BoxSettingFragment();
        Bundle args = new Bundle();
        args.putString("data", data);
        setting.setArguments(args);
        return setting;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.box_fragment_setting, container, false);
        return view;
    }
}
