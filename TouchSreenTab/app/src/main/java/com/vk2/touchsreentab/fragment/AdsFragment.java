package com.vk2.touchsreentab.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.utils.MyAnimator;

public class AdsFragment extends Fragment {
    private ImageView imgIcon;

    public static AdsFragment newInstance(@NonNull Bundle bundle) {
        AdsFragment adsFragment = new AdsFragment();
        adsFragment.setArguments(bundle);
        return adsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ads, container, false);
        imgIcon = view.findViewById(R.id.icon);
        if (getArguments() != null)
            show(getArguments().getInt("res"));
        return view;
    }

    public void show(int icon) {
        imgIcon.setImageResource(icon);
        MyAnimator.getInstance().translate(imgIcon);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (imgIcon != null) {
            imgIcon.clearAnimation();
        }
    }
}
