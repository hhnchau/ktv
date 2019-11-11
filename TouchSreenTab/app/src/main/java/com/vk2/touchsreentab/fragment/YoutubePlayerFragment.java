package com.vk2.touchsreentab.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vk2.touchsreentab.R;

public class  YoutubePlayerFragment extends BaseFragment {
    public static YoutubePlayerFragment newInstance(@NonNull Bundle bundle) {
        YoutubePlayerFragment youtubePlayerFragment = new YoutubePlayerFragment();
        //videoFragment.init(bundle);
        return youtubePlayerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.youtube_player_fragment, container, false);

        return view;
    }

}
