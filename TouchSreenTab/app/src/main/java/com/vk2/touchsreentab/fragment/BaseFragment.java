package com.vk2.touchsreentab.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.database.entity.Song;
import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentez;
import com.vk2.touchsreentab.model.PageControl;
import com.vk2.touchsreentab.model.viewmodel.PageControlViewModel;
import com.vk2.touchsreentab.model.viewmodel.PlaylistModelView;

public abstract class BaseFragment extends Fragment {
    private PageControlViewModel pageControlViewModel;
    private PlaylistModelView playlistModelView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() == null) return;
        pageControlViewModel = ViewModelProviders.of(getActivity()).get(PageControlViewModel.class);
        playlistModelView = ViewModelProviders.of(getActivity()).get(PlaylistModelView.class);
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

    protected void pageControl(Fragmentez frg, int direction) {
        if (pageControlViewModel == null && getActivity() != null)
            pageControlViewModel = ViewModelProviders.of(getActivity()).get(PageControlViewModel.class);
        pageControlViewModel.setPageControl(new PageControl(frg, direction));
    }

    protected void updatePlaylist(Song song, int type) {
        playlistModelView.setValue(song, type);
    }

    public Fragment getFragmentByTag(String tag) {
        if (getActivity() == null) return null;
        return getActivity().getSupportFragmentManager().findFragmentByTag(tag);
    }
}
