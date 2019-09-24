package com.vk2.touchsreentab.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentcz;
import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentez;
import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentoz;
import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmenttz;
import com.vk2.touchsreentab.model.PageControl;

import java.util.ArrayList;
import java.util.List;

public class PageFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    public static List<Fragmentoz> lstFrg;
    private LinearLayout boxRecommend, boxSongArtist, boxPlaylist, boxSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_page, container, false);
        initView();
        onFragmentChange(Fragmentez.RECOMMEND_FRAGMENT);
        return view;
    }

    public void onFragmentChange(Fragmentez frgez) {
        Fragmentcz.addFragment(lstFrg, getFragmentManager(), frgez, false, R.id.layout_container, null, Fragmentcz.NONE);
        Fragmenttz.setToolbar(getActivity(), frgez, boxRecommend, boxSongArtist, boxPlaylist, boxSearch);
    }


    @Override
    protected void onPageChange(int page, int totalPage) {
        ((TextView) view.findViewById(R.id.tvPages)).setText((page + "/" + totalPage));
    }

    @Override
    public int getCurrentPage() {
        return 0;
    }

    @Override
    public int getTotalPage() {
        return 0;
    }

    private void initView() {
        lstFrg = new ArrayList<>();
        boxRecommend = view.findViewById(R.id.boxRecommend);
        boxSongArtist = view.findViewById(R.id.boxSongArtist);
        boxPlaylist = view.findViewById(R.id.boxPlaylist);
        boxSearch = view.findViewById(R.id.boxSearch);
        setOnclickView();
    }

    View[] listPosition;

    private void setOnclickView() {
        //header
        view.findViewById(R.id.logo).setOnClickListener(this);
        view.findViewById(R.id.songs).setOnClickListener(this);
        view.findViewById(R.id.artists).setOnClickListener(this);
        view.findViewById(R.id.playList).setOnClickListener(this);
        view.findViewById(R.id.setting).setOnClickListener(this);
        view.findViewById(R.id.usb).setOnClickListener(this);
        view.findViewById(R.id.tvPlaylist).setOnClickListener(this);
        view.findViewById(R.id.tvHistory).setOnClickListener(this);
        view.findViewById(R.id.tvDownload).setOnClickListener(this);
        view.findViewById(R.id.tvComplex).setOnClickListener(this);
        view.findViewById(R.id.tvSoundCloud).setOnClickListener(this);
        view.findViewById(R.id.tvMixCloud).setOnClickListener(this);
        view.findViewById(R.id.tvYoutube).setOnClickListener(this);
        view.findViewById(R.id.imgPrevious).setOnClickListener(this);
        view.findViewById(R.id.imgNext).setOnClickListener(this);
        listPosition = new View[]{view.findViewById(R.id.position_recommended), view.findViewById(R.id.position_song), view.findViewById(R.id.position_artists), view.findViewById(R.id.position_playlist)};
    }

    private void showPositionFocus(int positionVisibility) {
        if (positionVisibility >= listPosition.length)
            return;
        for (int i = 0; i < listPosition.length; i++) {
            if (i == positionVisibility) {
                listPosition[i].setVisibility(View.VISIBLE);
            } else {
                listPosition[i].setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logo:
                onFragmentChange(Fragmentez.RECOMMEND_FRAGMENT);
                showPositionFocus(0);
                break;
            case R.id.songs:
                SongFragment songFragment = (SongFragment) getFragmentByTag(SongFragment.class.getName());
                if (songFragment != null) {
                    onPageChange(songFragment.getCurrentPage(), songFragment.getTotalPage());
                }
                onFragmentChange(Fragmentez.SONG_FRAGMENT);
                showPositionFocus(1);
                break;
            case R.id.artists:
                ArtistFragment artistFragment = (ArtistFragment) getFragmentByTag(ArtistFragment.class.getName());
                if (artistFragment != null) {
                    onPageChange(artistFragment.getCurrentPage(), artistFragment.getTotalPage());
                }
                onFragmentChange(Fragmentez.ARTIST_FRAGMENT);
                showPositionFocus(2);
                break;
            case R.id.playList:
                onFragmentChange(Fragmentez.PLAYLIST_FRAGMENT);
                showPositionFocus(3);
                break;
            case R.id.setting:
                onFragmentChange(Fragmentez.SETTING_FRAGMENT);
                break;
            case R.id.usb:
                break;
            case R.id.tvPlaylist:
                break;
            case R.id.tvHistory:
                break;
            case R.id.tvDownload:
                break;
            case R.id.tvComplex:
                onFragmentChange(Fragmentez.SEARCH_COMPLEX_FRAGMENT);
                break;
            case R.id.tvSoundCloud:
                break;
            case R.id.tvMixCloud:
                break;
            case R.id.tvYoutube:
                onFragmentChange(Fragmentez.SEARCH_YOUTUBE_FRAGMENT);
                break;
            case R.id.imgPrevious:
                pageControl(Fragmentcz.getCurrentFragment(), PageControl.PREVIOUS);
                break;
            case R.id.imgNext:
                pageControl(Fragmentcz.getCurrentFragment(), PageControl.NEXT);
                break;
        }
    }

}