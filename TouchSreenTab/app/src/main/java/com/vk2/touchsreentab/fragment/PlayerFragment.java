package com.vk2.touchsreentab.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.activity.MainActivity;

public class PlayerFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private int count = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_player, container, false);
        initView();
        return view;
    }

    private void initView() {
        view.findViewById(R.id.zoom).setOnClickListener(this);
        view.findViewById(R.id.control).setOnClickListener(this);
        view.findViewById(R.id.feeling).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.zoom:
                if (count % 2 == 0) {
                    ((MainActivity) getActivity()).playerFullScreen();
                } else {
                    ((MainActivity) getActivity()).playerSmallScreen();
                }
                count++;
                break;
            case R.id.control:
                break;
            case R.id.feeling:
                break;
        }
    }
}