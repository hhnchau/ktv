package com.vk2.touchsreentab.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.activity.MainActivity;
import com.vk2.touchsreentab.database.entity.Song;
import com.vk2.touchsreentab.fragment.fragmentcontroller.DiskFragment;
import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentcz;
import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentez;
import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentoz;
import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmenttz;
import com.vk2.touchsreentab.model.PageControl;
import com.vk2.touchsreentab.model.viewmodel.PlaylistModelView;
import com.vk2.touchsreentab.view.CustomDialogMessage;

import java.util.ArrayList;
import java.util.List;

public class PageFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    public static List<Fragmentoz> lstFrg;
    private LinearLayout boxRecommend, boxSongArtist, boxPlaylist, boxSearch;
    private View[] listArrow;
    private View[] listBackground;
    private TextView tvNumber;
    private ControlFragment controlFragment;
    private DiskFragment diskFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_page, container, false);
        initView();
        onFragmentChange(Fragmentez.RECOMMEND_FRAGMENT);
        controlFragment = (ControlFragment) getFragmentByTag(ControlFragment.class.getName());
        return view;
    }

    public void onFragmentChange(Fragmentez frgez) {
        Fragmentcz.addFragment(lstFrg, getFragmentManager(), frgez, false, R.id.layout_container, null, Fragmentcz.NONE);
        Fragmenttz.setToolbar(getActivity(), frgez, boxRecommend, boxSongArtist, boxPlaylist, boxSearch);
        if (frgez == Fragmentez.SEARCH_COMPLEX_FRAGMENT) {
            setActiveBackground(3);
            if (controlFragment != null) {
                controlFragment.hideButtonEnter();
            }
        } else if (frgez == Fragmentez.PLAYLIST_FRAGMENT)
            setActiveBackground(0);
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
        tvNumber = view.findViewById(R.id.tvNumber);
        boxRecommend = view.findViewById(R.id.boxRecommend);
        boxSongArtist = view.findViewById(R.id.boxSongArtist);
        boxPlaylist = view.findViewById(R.id.boxPlaylist);
        boxSearch = view.findViewById(R.id.boxSearch);
        setOnclickView();
    }

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
        view.findViewById(R.id.imgBack).setOnClickListener(this);
        listArrow = new View[]{
                view.findViewById(R.id.position_recommended),
                view.findViewById(R.id.position_song),
                view.findViewById(R.id.position_artists),
                view.findViewById(R.id.position_playlist)
        };
        listBackground = new View[]{
                view.findViewById(R.id.tvPlaylist),
                view.findViewById(R.id.tvHistory),
                view.findViewById(R.id.tvDownload),
                view.findViewById(R.id.tvComplex),
                view.findViewById(R.id.tvSoundCloud),
                view.findViewById(R.id.tvMixCloud),
                view.findViewById(R.id.tvYoutube),
        };

        if (getActivity() == null) return;
        PlaylistModelView playlistModelView = ViewModelProviders.of(getActivity()).get(PlaylistModelView.class);
        playlistModelView.getPlaylist().observe(getActivity(), new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                tvNumber.setText(String.valueOf(songs.size()));
            }
        });

    }

    private void showPositionFocus(int positionVisibility) {
        if (positionVisibility >= listArrow.length)
            return;
        for (int i = 0; i < listArrow.length; i++) {
            if (i == positionVisibility) {
                listArrow[i].setVisibility(View.VISIBLE);
            } else {
                listArrow[i].setVisibility(View.GONE);
            }
        }
    }

    private void setActiveBackground(int type) {
        for (int i = 0; i < listBackground.length; i++) {
            if (type == i) {
                listBackground[i].setBackgroundResource(R.drawable.rectangle);
            } else {
                listBackground[i].setBackgroundResource(0);
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logo:
                clearSearch();
                onFragmentChange(Fragmentez.RECOMMEND_FRAGMENT);
                showPositionFocus(0);
                break;
            case R.id.songs:
                clearSearch();
                SongFragment songFragment = (SongFragment) getFragmentByTag(SongFragment.class.getName());
                if (songFragment != null) {
                    onPageChange(songFragment.getCurrentPage(), songFragment.getTotalPage());
                }
                onFragmentChange(Fragmentez.SONG_FRAGMENT);
                showPositionFocus(1);
                break;
            case R.id.artists:
                clearSearch();
                ArtistFragment artistFragment = (ArtistFragment) getFragmentByTag(ArtistFragment.class.getName());
                if (artistFragment != null) {
                    onPageChange(artistFragment.getCurrentPage(), artistFragment.getTotalPage());
                }
                onFragmentChange(Fragmentez.ARTIST_FRAGMENT);
                showPositionFocus(2);
                break;
            case R.id.playList:
                clearSearch();
                onFragmentChange(Fragmentez.PLAYLIST_FRAGMENT);
                showPositionFocus(3);
                break;
            case R.id.setting:
                onFragmentChange(Fragmentez.SETTING_FRAGMENT);
                break;
            case R.id.usb:
                CustomDialogMessage dialogMessage = new CustomDialogMessage(getActivity(), new CustomDialogMessage.OnDialogMessageClick() {
                    @Override
                    public void onButtonOK() {
                        Toast.makeText(getContext(), "onButtonOK", Toast.LENGTH_SHORT).show();
                        ((MainActivity) getActivity()).pageFragmentFullSceen();
                        if (diskFragment == null) diskFragment = new DiskFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .add(R.id.framePage, diskFragment, DiskFragment.class.getName()).addToBackStack(DiskFragment.class.getName()).commit();
                    }

                    @Override
                    public void onButtonCancel() {
                        Toast.makeText(getContext(), "onButtonCancel", Toast.LENGTH_SHORT).show();

                    }
                });
                dialogMessage.show();
                break;
            case R.id.tvPlaylist:
                setActiveBackground(0);
                break;
            case R.id.tvHistory:
                setActiveBackground(1);
                break;
            case R.id.tvDownload:
                setActiveBackground(2);
                break;
            case R.id.tvComplex:
                setActiveBackground(3);
                onFragmentChange(Fragmentez.SEARCH_COMPLEX_FRAGMENT);
                if (controlFragment != null) {
                    controlFragment.hideButtonEnter();
                }
                break;
            case R.id.tvSoundCloud:
                setActiveBackground(4);
                onFragmentChange(Fragmentez.SEARCH_SOUNDCLOUND_FRAGMENT);
                if (controlFragment != null) {
                    controlFragment.showButtonEnter();
                }
                break;
            case R.id.tvMixCloud:
                setActiveBackground(5);
                break;
            case R.id.tvYoutube:
                setActiveBackground(6);
                onFragmentChange(Fragmentez.SEARCH_YOUTUBE_FRAGMENT);
                if (controlFragment != null) {
                    controlFragment.showButtonEnter();
                }
                break;
            case R.id.imgPrevious:
                pageControl(Fragmentcz.getCurrentFragment(), PageControl.PREVIOUS);
                break;
            case R.id.imgNext:
                pageControl(Fragmentcz.getCurrentFragment(), PageControl.NEXT);
                break;
            case R.id.imgBack:
                onFragmentChange(Fragmentez.RECOMMEND_FRAGMENT);
                break;
        }
    }

    private void clearSearch() {
        if (controlFragment != null && !controlFragment.isHidden())
            controlFragment.clearSearch();
    }

}