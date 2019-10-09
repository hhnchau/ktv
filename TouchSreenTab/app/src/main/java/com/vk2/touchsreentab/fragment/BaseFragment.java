package com.vk2.touchsreentab.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.aplication.MyApplication;
import com.vk2.touchsreentab.database.entity.Song;
import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentez;
import com.vk2.touchsreentab.model.Ablum;
import com.vk2.touchsreentab.model.PageControl;
import com.vk2.touchsreentab.model.viewmodel.PageControlViewModel;
import com.vk2.touchsreentab.model.viewmodel.PlaylistModelView;
import com.vk2.touchsreentab.model.viewmodel.SingerVewModel;
import com.vk2.touchsreentab.model.viewmodel.SongViewModel;
import com.vk2.touchsreentab.model.viewmodel.TextSearchViewModel;
import com.vk2.touchsreentab.view.CustomSongDialog;

public abstract class BaseFragment extends Fragment {
    private PageControlViewModel pageControlViewModel;
    private PlaylistModelView playlistModelView;
    private SingerVewModel singerVewModel;
    private SongViewModel songViewModel;
    private TextSearchViewModel textSearchViewModel;
    private PageFragment pageFragment;
    private ControlFragment controlFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() == null) return;
        pageFragment = (PageFragment) getFragmentByTag(PageFragment.class.getName());
        controlFragment = (ControlFragment) getFragmentByTag(ControlFragment.class.getName());
        pageControlViewModel = ViewModelProviders.of(getActivity()).get(PageControlViewModel.class);
        playlistModelView = ViewModelProviders.of(getActivity()).get(PlaylistModelView.class);
        singerVewModel = ViewModelProviders.of(getActivity()).get(SingerVewModel.class);
        songViewModel = ViewModelProviders.of(getActivity()).get(SongViewModel.class);
        textSearchViewModel = ViewModelProviders.of(getActivity()).get(TextSearchViewModel.class);
    }

    public DividerItemDecoration getDivider(LinearLayoutManager layoutManager) {
        if (getActivity() == null) return null;
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(getActivity().getResources().getDrawable(R.drawable.divider));
        return dividerItemDecoration;
    }

    protected void onPageChange(int page, int totalPage) {
    }

    public void updateCurrentPage(int currentPage, int totalPage) {
        if (pageFragment != null) {
            pageFragment.onPageChange(currentPage, totalPage);
        }
    }


    protected void onResumeFragment() {

    }

    public SingerVewModel getSingerVewModel() {
        return singerVewModel;
    }

    public SongViewModel getSongViewModel() {
        return songViewModel;
    }

    public PlaylistModelView getPlaylistModelView() {
        return playlistModelView;
    }

    public PageControlViewModel getPageControlViewModel() {
        return pageControlViewModel;
    }

    public TextSearchViewModel getTextSearchViewModel() {
        return textSearchViewModel;
    }

    protected void pageControl(Fragmentez frg, int direction) {
        getPageControlViewModel().setPageControl(new PageControl(frg, direction));
    }

    public Fragment getFragmentByTag(String tag) {
        if (getActivity() == null) return null;
        return getActivity().getSupportFragmentManager().findFragmentByTag(tag);
    }

    protected void gotoComplex() {
        if (pageFragment != null) {
            pageFragment.onFragmentChange(Fragmentez.SEARCH_COMPLEX_FRAGMENT);
        }
    }

    protected void gotoRecommend() {
        if (pageFragment != null) {
            pageFragment.onFragmentChange(Fragmentez.RECOMMEND_FRAGMENT);
        }
    }

    protected void gotoSinger(Integer idSinger, String singerName) {
        if (pageFragment != null) {
            pageFragment.onFragmentChange(Fragmentez.SINGER_FRAGMENT);
            pageFragment.setTitleTopBar(singerName);
            singerVewModel.idSinger.setValue(idSinger);
        }
    }

    protected void backStackFragment() {
        if (pageFragment != null) {
            pageFragment.onFragmentChange(pageFragment.getBackStackFragment());
        }
    }

    protected void showDialog(Song song) {
        CustomSongDialog dialog = new CustomSongDialog(getContext(), song, new CustomSongDialog.OnButtonDialogClick() {
            @Override
            public void onSelected() {
                Toast.makeText(getContext(), "onSelected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInsertedTop() {
                Toast.makeText(getContext(), "onInsertedTop", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    protected void handleBanner(Ablum album) {
        Toast.makeText(getActivity(), "Xu ly Album", Toast.LENGTH_SHORT).show();
    }

    protected void updatePlaylist(Song song, int type) {
        playlistModelView.setValue(getActivity(), song, type);
    }

    protected void showBackButton() {
        if (pageFragment != null) pageFragment.showBackButton();
    }

    protected void hideBackButton() {
        if (pageFragment != null) pageFragment.hideBackButton();
    }

    protected void showEnterButton() {
        if (controlFragment != null) controlFragment.showEnterButton();
    }

    protected void hideEnterButton() {
        if (controlFragment != null) controlFragment.hideEnterButton();
    }

    protected void clearTextSearch() {
        if (controlFragment != null) controlFragment.clearTextSearch();
    }

    protected void showTopBar() {
        if (pageFragment != null) pageFragment.showTopBar();
    }

    protected void hideTopBar() {
        if (pageFragment != null) pageFragment.hideTopBar();
    }
}
