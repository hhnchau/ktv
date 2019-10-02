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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() == null) return;
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
        PageFragment pageFragment = (PageFragment) getFragmentByTag(PageFragment.class.getName());
        if (pageFragment != null) {
            pageFragment.onPageChange(currentPage, totalPage);
        }
    }

    protected int getCurrentPage() {
        return 0;
    }

    protected int getTotalPage() {
        return 0;
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
        if (pageControlViewModel == null && getActivity() != null)
            pageControlViewModel = ViewModelProviders.of(getActivity()).get(PageControlViewModel.class);
        pageControlViewModel.setPageControl(new PageControl(frg, direction));
    }

    public Fragment getFragmentByTag(String tag) {
        if (getActivity() == null) return null;
        return getActivity().getSupportFragmentManager().findFragmentByTag(tag);
    }

    protected void gotoSinger(Integer idSinger) {
        PageFragment pageFragment = (PageFragment) getFragmentByTag(PageFragment.class.getName());
        if (pageFragment != null) {
            pageFragment.onFragmentChange(Fragmentez.SINGER_FRAGMENT);
            singerVewModel.idSinger.setValue(idSinger);
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

    protected void updatePlaylist(Song song, int type) {
        playlistModelView.setValue(getActivity(), song, type);
    }
}
