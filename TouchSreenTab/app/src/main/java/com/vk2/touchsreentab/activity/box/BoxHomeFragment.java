package com.vk2.touchsreentab.activity.box;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.activity.SingleMode;

public class BoxHomeFragment extends BaseFragmentBox implements View.OnClickListener {
    public static BoxHomeFragment newInstance(String data) {
        BoxHomeFragment home = new BoxHomeFragment();
        Bundle args = new Bundle();
        args.putString("data", data);
        home.setArguments(args);
        return home;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.box_fragment_home, container, false);
        view.findViewById(R.id.banner).setOnClickListener(this);
        view.findViewById(R.id.recommend).setOnClickListener(this);
        view.findViewById(R.id.songs).setOnClickListener(this);
        view.findViewById(R.id.artists).setOnClickListener(this);
        view.findViewById(R.id.playlist).setOnClickListener(this);
        view.findViewById(R.id.setting).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (getActivity() != null)
            ((SingleMode) getActivity()).removeHandler();
        switch (view.getId()) {
            case R.id.banner:
                Toast.makeText(getActivity(), "Banner", Toast.LENGTH_SHORT).show();
                break;
            case R.id.recommend:
                Toast.makeText(getActivity(), "recommend", Toast.LENGTH_SHORT).show();
                break;
            case R.id.songs:
                Toast.makeText(getActivity(), "songs", Toast.LENGTH_SHORT).show();
                break;
            case R.id.artists:
                Toast.makeText(getActivity(), "artists", Toast.LENGTH_SHORT).show();
                break;
            case R.id.playlist:
                Toast.makeText(getActivity(), "playlist", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(getActivity(), "setting", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
