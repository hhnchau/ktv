package com.vk2.touchsreentab.fragment;import android.arch.lifecycle.Observer;import android.arch.lifecycle.ViewModelProviders;import android.arch.paging.PagedList;import android.os.Bundle;import android.support.annotation.NonNull;import android.support.annotation.Nullable;import android.support.v7.widget.GridLayoutManager;import android.support.v7.widget.LinearLayoutManager;import android.support.v7.widget.RecyclerView;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.Toast;import com.vk2.touchsreentab.R;import com.vk2.touchsreentab.adapter.ArtistAdapter;import com.vk2.touchsreentab.aplication.MyApplication;import com.vk2.touchsreentab.database.entity.Singer;import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentez;import com.vk2.touchsreentab.model.PageControl;import com.vk2.touchsreentab.model.viewmodel.PageControlViewModel;import com.vk2.touchsreentab.model.viewmodel.SingerVewModel;import com.vk2.touchsreentab.model.viewmodel.SongViewModel;public class ArtistFragment extends BaseFragment {    private View view;    private int currentPage = 1;    private int numberItem = 4;    private int totalPage = 0;    @Nullable    @Override    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {        view = inflater.inflate(R.layout.fragment_artist, container, false);        initArtist();        return view;    }    private void initArtist() {        if (getActivity() == null) return;        final RecyclerView recyclerView = view.findViewById(R.id.rcvArtists);        recyclerView.setNestedScrollingEnabled(false);        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4, GridLayoutManager.VERTICAL, false));        final ArtistAdapter mAdapter = new ArtistAdapter();        recyclerView.setAdapter(mAdapter);        final SingerVewModel singerVewModel = ViewModelProviders.of(getActivity()).get(SingerVewModel.class);        singerVewModel.getAllSinger(MyApplication.appDatabase.singerDao());        singerVewModel.listSinger.observe(getActivity(), new Observer<PagedList<Singer>>() {            @Override            public void onChanged(@Nullable PagedList<Singer> artists) {                if (artists != null) {                    totalPage = (int) Math.ceil((double) artists.size() / numberItem);                    updateCurrentPage(currentPage, totalPage);                }                mAdapter.submitList(artists);            }        });        final PageControlViewModel pageControlViewModel = ViewModelProviders.of(getActivity()).get(PageControlViewModel.class);        pageControlViewModel.getPageControl().observe(getActivity(), new Observer<PageControl>() {            @Override            public void onChanged(PageControl pageControl) {                if (pageControl.getFrg() == Fragmentez.ARTIST_FRAGMENT) {                    if (pageControl.getDirection() == PageControl.NEXT) {                        recyclerView.post(new Runnable() {                            @Override                            public void run() {                                currentPage++;                                int nextPosition = currentPage * numberItem - 1;                                recyclerView.smoothScrollToPosition(nextPosition);                            }                        });                    } else if (pageControl.getDirection() == PageControl.PREVIOUS) {                        recyclerView.post(new Runnable() {                            @Override                            public void run() {                                currentPage--;                                int prevPosition = currentPage * numberItem - numberItem;                                recyclerView.smoothScrollToPosition(prevPosition);                            }                        });                    }                }            }        });        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {            @Override            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {                super.onScrollStateChanged(recyclerView, newState);                if (newState == RecyclerView.SCROLL_STATE_IDLE) {                    int position;                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();                    if (linearLayoutManager != null) {                        position = linearLayoutManager.findLastVisibleItemPosition();                        currentPage = position / numberItem;                        if (currentPage <= 0) return;                        int current = position % (currentPage * numberItem);                        if (current > 0) currentPage++;                        updateCurrentPage(currentPage, totalPage);                    }                }            }        });        mAdapter.setOnItemClick(new ArtistAdapter.OnItemClick() {            @Override            public void onClick(Singer singer) {                PageFragment pageFragment = (PageFragment) getFragmentByTag(PageFragment.class.getName());                if (pageFragment != null) {                    pageFragment.onFragmentChange(Fragmentez.SINGER_FRAGMENT);                    singerVewModel.getAllSongBySinger(MyApplication.appDatabase.songDao(), singer.getId());                }            }        });    }    @Override    public int getCurrentPage() {        return currentPage;    }    @Override    public int getTotalPage() {        return totalPage;    }}